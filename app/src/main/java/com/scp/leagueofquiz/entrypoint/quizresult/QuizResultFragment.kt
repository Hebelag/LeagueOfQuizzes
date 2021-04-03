package com.scp.leagueofquiz.entrypoint.quizresult

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.api.database.item.Item
import com.scp.leagueofquiz.databinding.QuizResultFragmentBinding
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.time.Duration
import java.util.*

@AndroidEntryPoint
class QuizResultFragment : Fragment(R.layout.quiz_result_fragment) {
    private val viewModel: QuizResultViewModel by viewModels()
    private val binding by viewBinding(QuizResultFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argsBundle = arguments
        if (argsBundle != null) {
            val args = QuizResultFragmentArgs.fromBundle(argsBundle)
            viewModel.quizMode = args.quizmode
            viewModel.score.value = args.score
            viewModel.timer.value = Duration.ofMillis(args.timer)
            viewModel.failedAttempts.value = args.failedAttempts
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exitButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(
                            QuizResultFragmentDirections.actionQuizResultFragmentToMainMenuFragment())
        }
        binding.itemView.setOnClickListener {viewModel.chooseImage()}
        setScoreLineVisibility(viewModel.quizMode)
        viewModel.score.observe(viewLifecycleOwner, { score: Int -> setScore(score) })
        viewModel.failedAttempts.observe(viewLifecycleOwner, { failedAttempts: Int -> failedAttempt(failedAttempts) })
        viewModel.timer.observe(viewLifecycleOwner, { duration: Duration -> setTimer(duration) })
        viewModel.itemImage.observe(viewLifecycleOwner, ::setItemImage)
    }

    private fun setItemImage(item: Item) {
        try {
            // get input stream
            val ims: InputStream = requireContext().assets
                    .open("image_drawables/${item.image.full}", AssetManager.ACCESS_BUFFER)
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            binding.itemView.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
            Timber.e(ex)
        }
    }

    private fun setTimer(duration: Duration) {
        val timeString = if (duration.toHours() > 0) {
            String.format(
                    Locale.getDefault(),
                    "%d:%02d:%02d",
                    duration.toHours(),
                    duration.toMinutes(),
                    duration.seconds % 60)
        } else {
            @Suppress("ControlFlowWithEmptyBody")
            if (duration.isZero) {
                // Should something happen here?
            }
            String.format(
                    Locale.getDefault(), "%d:%02d", duration.toMinutes(), duration.seconds % 60)
        }
        binding.resultTime.text = timeString
    }

    private fun failedAttempt(failedAttempts: Int) {
        binding.resultWrong.text = getString(R.string.result_wrong_champions, failedAttempts)
    }

    private fun setScore(score: Int) {
        if (viewModel.quizMode == QuizMode.TIME) {
            binding.resultScoreTimeAttack.text = getString(R.string.result_final_score, score)
        } else {
            binding.resultScoreNonTime.text = getString(R.string.result_champions, score)
        }
    }

    private fun setScoreLineVisibility(quizMode: QuizMode) {
        if (quizMode == QuizMode.TIME) {
            binding.resultScoreTimeAttack.visibility = View.VISIBLE
            binding.resultScoreNonTime.visibility = View.INVISIBLE
        } else {
            binding.resultScoreTimeAttack.visibility = View.INVISIBLE
            binding.resultScoreNonTime.visibility = View.VISIBLE
        }
    }
}