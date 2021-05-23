package com.scp.leagueofquiz.entrypoint.texttopictures

import android.annotation.SuppressLint
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
import com.scp.leagueofquiz.databinding.TextToPicturesFragmentBinding
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.time.Duration
import java.time.Instant
import java.util.*

@AndroidEntryPoint
class TextToPicturesFragment : Fragment(R.layout.text_to_pictures_fragment) {
    private val viewModel: TextToPicturesViewModel by viewModels()
    private val binding by viewBinding(TextToPicturesFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.timer.value = Duration.ZERO
        val argsBundle = arguments
        if (argsBundle != null) {
            val args = TextToPicturesFragmentArgs.fromBundle(argsBundle)
            viewModel.quizType = args.quizType
        }
        if (viewModel.timer.value.isNegative) {
            viewModel.timer.value = Duration.ZERO
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObservers()
    }

    private fun setupUi() = with(binding) {
        setScoreLineVisibility()
        setTimeAttackLineVisibility()
        startQuizButton.setOnClickListener(::startQuiz)
    }

    private fun setupObservers() = with(viewModel) {
        imageGrid.observe(viewLifecycleOwner, ::setImageGrid)
        score.observe(viewLifecycleOwner, ::setScore)
        failedAttempts.observe(viewLifecycleOwner, ::failedAttempt)
        startTime.observe(viewLifecycleOwner, ::setupStartButton)
        timer.observe(viewLifecycleOwner, ::setTimer)
        rightImage.observe(viewLifecycleOwner, ::setRightInstanceName)
        quizFinished.observe(viewLifecycleOwner, ::checkQuizFinished)
        buttonText.observe(viewLifecycleOwner, ::buttonText)
    }

    private fun buttonText(s: String) {
        binding.startQuizButton.text = s
    }

    @SuppressLint("SetTextI18n")
    private fun failedAttempt(integer: Int?) {
        if (integer != null && integer > 0) {
            Toast.makeText(requireContext(), "WRONG!", Toast.LENGTH_SHORT).show()
            binding.wrongViewNonTime.text = integer.toString()
        }
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
                            TextToPicturesFragmentDirections.goToResult(
                                    score,
                                    viewModel.timer.value.toMillis(),
                                    failedAttempts))
        }
    }

    private fun setRightInstanceName(instance: Pair<String, Image>) {
        binding.championText.text = instance.first
    }

    private fun pickAnswer(view: View) {
        val instanceIndex = view.tag.toString().toInt()
        viewModel.pickAnswer(instanceIndex)
    }

    private fun setTimer(timer: Duration) {
        val timeString = String.format(Locale.getDefault(), "%d:%02d", timer.toMinutes(), timer.seconds % 60)
        binding.timer.text = timeString
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setupStartButton(startTime: Instant) = with(binding) {
        startQuizButton.isClickable = !viewModel.isQuizRunning
        if (viewModel.isQuizRunning) {
            startQuizButton.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.grey))
            btnAns1.setOnClickListener { view: View -> pickAnswer(view) }
            btnAns2.setOnClickListener { view: View -> pickAnswer(view) }
            btnAns3.setOnClickListener { view: View -> pickAnswer(view) }
            btnAns4.setOnClickListener { view: View -> pickAnswer(view) }
        } else {
            startQuizButton.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_500))
            btnAns1.setOnClickListener(null)
            btnAns2.setOnClickListener(null)
            btnAns3.setOnClickListener(null)
            btnAns4.setOnClickListener(null)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setScore(score: Int?) {
        binding.scoreViewNonTime.text = score?.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun startQuiz(view: View) {
        viewModel.startQuiz()
    }

    private fun setImageGrid(images: List<Pair<String, Image>>) = with(binding) {
        btnAns1.setImageDrawable(getImage(images[0].second.full, viewModel.quizType))
        btnAns2.setImageDrawable(getImage(images[1].second.full, viewModel.quizType))
        btnAns3.setImageDrawable(getImage(images[2].second.full, viewModel.quizType))
        btnAns4.setImageDrawable(getImage(images[3].second.full, viewModel.quizType))
    }

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

    private fun setTimeAttackLineVisibility() = with(binding) {
        timeAttackLayout.visibility = View.INVISIBLE
    }

    private fun setScoreLineVisibility() = with(binding) {
        scoreLineTimeAttack.visibility = View.INVISIBLE
        scoreLineNonTime.visibility = View.VISIBLE
    }
}