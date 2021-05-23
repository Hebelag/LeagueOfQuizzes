package com.scp.leagueofquiz.entrypoint.picturetotexts

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.api.database.shared.Image
import com.scp.leagueofquiz.databinding.PictureToTextsFragmentBinding
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.time.Duration
import java.util.*

@AndroidEntryPoint
class PictureToTexts : Fragment(R.layout.picture_to_texts_fragment) {
    private val viewModel: PictureToTextsViewModel by viewModels()
    private val binding by viewBinding(PictureToTextsFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argsBundle = arguments
        if (argsBundle != null) {
            val args = PictureToTextsArgs.fromBundle(argsBundle)
            viewModel.quizType = args.quizType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewModel) {
        super.onViewCreated(view, savedInstanceState)

        imageGrid.observe(viewLifecycleOwner, ::setTextGrid)
        score.observe(viewLifecycleOwner, ::setScore)
        failedAttempts.observe(viewLifecycleOwner, ::failedAttempt)
        startTime.observe(viewLifecycleOwner, { setupStartButton() })
        timer.observe(viewLifecycleOwner, ::setTimer)
        rightText.observe(viewLifecycleOwner, ::setRightInstancePicture)
        quizFinished.observe(viewLifecycleOwner, ::checkQuizFinished)
        buttonStartText.observe(viewLifecycleOwner, ::buttonStartText)

        binding.startQuizButtonPicToTexts.setOnClickListener { startQuiz() }


    }

    private fun buttonStartText(s: String) {
        binding.startQuizButtonPicToTexts.text = s
    }

    private fun checkQuizFinished(isQuizFinished: Boolean) {
        if (isQuizFinished) {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        val score = viewModel.score.value
        val failedAttempts = viewModel.failedAttempts.value
        if (score != null && failedAttempts != null) {
            NavHostFragment.findNavController(this)
                    .navigate(
                            PictureToTextsDirections.goToResultFromPicToTexts(
                                    score,
                                    viewModel.timer.value.toMillis(),
                                    failedAttempts))
        }
    }

    private fun setRightInstancePicture(pair: Pair<String, Image>) =
            binding.instanceImage.setImageDrawable(getImage(pair.second.full, viewModel.quizType))

    private fun setTimer(duration: Duration) {
        val timeString = String.format(Locale.getDefault(), "%d:%02d", duration.toMinutes(), duration.seconds % 60)
        binding.timer.text = timeString
    }

    private fun failedAttempt(integer: Int?) {
        if (integer != null && integer > 0) {
            Toast.makeText(requireContext(), "WRONG!", Toast.LENGTH_SHORT).show()
            binding.wrongViewNonTime.text = integer.toString()
        }
    }

    private fun setupStartButton() = with(binding) {
        startQuizButtonPicToTexts.isClickable = !viewModel.isQuizRunning

        if (viewModel.isQuizRunning) {
            startQuizButtonPicToTexts.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.grey))
            textAns1.setOnClickListener { view: View -> pickAnswer(view) }
            textAns2.setOnClickListener { view: View -> pickAnswer(view) }
            textAns3.setOnClickListener { view: View -> pickAnswer(view) }
            textAns4.setOnClickListener { view: View -> pickAnswer(view) }
        } else {
            startQuizButtonPicToTexts.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_500))
            textAns1.setOnClickListener(null)
            textAns2.setOnClickListener(null)
            textAns3.setOnClickListener(null)
            textAns4.setOnClickListener(null)
        }
    }

    private fun pickAnswer(view: View) = with(binding) {
        when (view.id) {
            textAns1.id -> viewModel.pickAnswer(textAns1.text.toString())
            textAns2.id -> viewModel.pickAnswer(textAns2.text.toString())
            textAns3.id -> viewModel.pickAnswer(textAns3.text.toString())
            textAns4.id -> viewModel.pickAnswer(textAns4.text.toString())
        }
    }

    private fun setScore(score: Int?) {
        binding.scoreViewNonTime.text = score?.toString()

    }

    private fun setTextGrid(textGrid: List<Pair<String, Image>>) = with(binding) {
        textAns1.text = textGrid[0].first
        textAns2.text = textGrid[1].first
        textAns3.text = textGrid[2].first
        textAns4.text = textGrid[3].first
    }

    private fun startQuiz() = viewModel.startQuiz()

    private fun getImage(endPath: String, quizType: QuizType) = try {
        val imagePath = when (quizType) {
            QuizType.CHAMPION -> "champion_drawables"
            QuizType.ABILITY -> "ability_drawables"
            QuizType.ITEM -> "image_drawables"
        }
        val ims: InputStream = requireContext().assets
                .open("$imagePath/$endPath", AssetManager.ACCESS_BUFFER)
        // create drawable from input stream
        val result = Drawable.createFromStream(ims, null)
        ims.close()
        result
    } catch (ex: IOException) {
        Timber.e(ex)
        null
    }
}