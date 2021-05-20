package com.scp.leagueofquiz.entrypoint.picturetotexts

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.api.database.shared.Image
import com.scp.leagueofquiz.databinding.PictureToTextsFragmentBinding
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import com.scp.leagueofquiz.entrypoint.texttopictures.TextToPicturesFragmentDirections
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.time.Duration
import java.time.Instant
import java.util.*

@AndroidEntryPoint
class PictureToTexts : Fragment(R.layout.picture_to_texts_fragment) {
    private val viewModel: PictureToTextsViewModel by viewModels()
    private val binding by viewBinding(PictureToTextsFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argsBundle = arguments
        if (argsBundle != null){
            val args = PictureToTextsArgs.fromBundle(argsBundle)
            viewModel.quizType = args.quizType
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.imageGrid.observe(viewLifecycleOwner, ::setTextGrid)
        viewModel.score.observe(viewLifecycleOwner, ::setScore)
        viewModel.failedAttempts.observe(viewLifecycleOwner, ::failedAttempt)
        viewModel.startTime.observe(viewLifecycleOwner, ::setupStartButton)
        viewModel.timer.observe(viewLifecycleOwner, ::setTimer)
        viewModel.rightText.observe(viewLifecycleOwner,::setRightInstancePicture)
        viewModel.quizFinished.observe(viewLifecycleOwner, ::checkQuizFinished)
        viewModel.buttonStartText.observe(viewLifecycleOwner, ::buttonStartText)

        binding.startQuizButtonPicToTexts.setOnClickListener(::startQuiz)


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

    private fun setRightInstancePicture(pair: Pair<String, Image>) {
        binding.instanceImage.setImageDrawable(getImage(pair.second.full,viewModel.quizType))
    }

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

    private fun setupStartButton(startTime: Instant) {
        binding.startQuizButtonPicToTexts.isClickable = !viewModel.isQuizRunning

        if (viewModel.isQuizRunning) {
            binding.startQuizButtonPicToTexts.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.grey))
            binding.textAns1.setOnClickListener { view: View -> pickAnswer(view) }
            binding.textAns2.setOnClickListener { view: View -> pickAnswer(view) }
            binding.textAns3.setOnClickListener { view: View -> pickAnswer(view) }
            binding.textAns4.setOnClickListener { view: View -> pickAnswer(view) }
        } else {
            binding.startQuizButtonPicToTexts.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_500))
            binding.textAns1.setOnClickListener(null)
            binding.textAns2.setOnClickListener(null)
            binding.textAns3.setOnClickListener(null)
            binding.textAns4.setOnClickListener(null)
        }
    }

    private fun pickAnswer(view: View) {
        when(view.id){
            binding.textAns1.id -> viewModel.pickAnswer(binding.textAns1.text.toString())
            binding.textAns2.id -> viewModel.pickAnswer(binding.textAns2.text.toString())
            binding.textAns3.id -> viewModel.pickAnswer(binding.textAns3.text.toString())
            binding.textAns4.id -> viewModel.pickAnswer(binding.textAns4.text.toString())
        }
    }

    private fun setScore(score: Int?) {
        binding.scoreViewNonTime.text = score?.toString()

    }

    private fun setTextGrid(textGrid: List<Pair<String, Image>>) {
        binding.textAns1.text = textGrid[0].first
        binding.textAns2.text = textGrid[1].first
        binding.textAns3.text = textGrid[2].first
        binding.textAns4.text = textGrid[3].first
    }

    private fun startQuiz(view: View?) {
        viewModel.startQuiz()
    }

    private fun getImage(endPath: String, quizType: QuizType): Drawable? {
        var d: Drawable? = null
        try {

            val imagePath = when(quizType){
                QuizType.CHAMPION -> "champion_drawables"
                QuizType.ABILITY -> "ability_drawables"
                QuizType.ITEM -> "image_drawables"
            }
            val ims: InputStream = requireContext().assets
                    .open("$imagePath/$endPath", AssetManager.ACCESS_BUFFER)
            // create drawable from input stream
            d = Drawable.createFromStream(ims, null)
            ims.close()

        } catch (ex: IOException) {
            Timber.e(ex)
        }
        // return the drawable
        return d
    }

}