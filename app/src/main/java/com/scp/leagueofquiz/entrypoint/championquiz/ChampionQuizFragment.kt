package com.scp.leagueofquiz.entrypoint.championquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.databinding.ChampionQuizFragmentBinding
import com.scp.leagueofquiz.entrypoint.championquiz.ChampionQuizViewModel
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.Instant
import java.util.*

@AndroidEntryPoint
class ChampionQuizFragment : Fragment(R.layout.champion_quiz_fragment) {
    private val viewModel: ChampionQuizViewModel by viewModels()
    private val binding  by viewBinding(ChampionQuizFragmentBinding::bind)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.timer.value = Duration.ZERO
        val argsBundle = arguments
        if (argsBundle != null) {
            val args = ChampionQuizFragmentArgs.fromBundle(argsBundle)
            viewModel.quizMode = args.mode
            viewModel.setChampionCount(args.champCount)
            viewModel.timer.value = Duration.ofMillis(args.time.toLong())
            if (viewModel.timer.value.isNegative) {
                viewModel.timer.value = Duration.ZERO
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup UI
        setScoreLineVisibility(viewModel.quizMode)
        setTimeAttackLineVisibility(viewModel.quizMode)
        binding.startQuizButton.setOnClickListener(::startQuiz)

        // Setup observers
        viewModel.championGrid.observe(viewLifecycleOwner, ::setChampionsGrid)
        viewModel.score.observe(viewLifecycleOwner, ::setScore)
        viewModel.failedAttempts.observe(viewLifecycleOwner, ::failedAttempt)
        viewModel.startTime.observe(viewLifecycleOwner, ::setupStartButton)
        viewModel.timer.observe(viewLifecycleOwner, ::setTimer)
        viewModel.rightChampion.observe(viewLifecycleOwner,::setRightChampionName)
        viewModel.quizFinished.observe(viewLifecycleOwner, ::checkQuizFinished)
        viewModel.buttonText.observe(viewLifecycleOwner, ::buttonText)
    }

    private fun buttonText(s: String) {
        binding.startQuizButton.text = s
    }

    @SuppressLint("SetTextI18n")
    private fun failedAttempt(integer: Int?) {
        if (integer != null && integer > 0) {
            Toast.makeText(requireContext(), "WRONG!", Toast.LENGTH_SHORT).show()
            if (viewModel.quizMode == QuizMode.TIME) {
                binding.wrongsViewTimeAttack.text = integer.toString()
            } else {
                binding.wrongViewNonTime.text = integer.toString()
            }
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
                            ChampionQuizFragmentDirections.goToResult(
                                    score,
                                    viewModel.timer.value.toMillis(),
                                    viewModel.quizMode,
                                    failedAttempts))
        }
    }

    private fun setRightChampionName(champion: Champion) {
        binding.championText.text = champion.name
    }

    private fun pickAnswer(view: View) {
        val champIndex = view.tag.toString().toInt()
        viewModel.pickAnswer(champIndex)
    }

    private fun setTimer(timer: Duration) {
        val timeString = String.format(Locale.getDefault(), "%d:%02d", timer.toMinutes(), timer.seconds % 60)
        if (viewModel.quizMode == QuizMode.TIME) {
            if (timer.isZero) {
                navigateToResult()
            }
            binding.countdownView.text = timeString
        } else {
            binding.timer.text = timeString
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setupStartButton(startTime: Instant) {
        binding.startQuizButton.isClickable = !viewModel.isQuizRunning
        if (viewModel.isQuizRunning) {
            if (viewModel.quizMode == QuizMode.ENDLESS) {
                binding.startQuizButton.setText(R.string.buttonStopText)
                binding.startQuizButton.isClickable = true
            } else {
                binding.startQuizButton.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.grey))
            }
            binding.btnAns1.setOnClickListener { view: View -> pickAnswer(view) }
            binding.btnAns2.setOnClickListener { view: View -> pickAnswer(view) }
            binding.btnAns3.setOnClickListener { view: View -> pickAnswer(view) }
            binding.btnAns4.setOnClickListener { view: View -> pickAnswer(view) }
        } else {
            binding.startQuizButton.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_500))
            binding.btnAns1.setOnClickListener(null)
            binding.btnAns2.setOnClickListener(null)
            binding.btnAns3.setOnClickListener(null)
            binding.btnAns4.setOnClickListener(null)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setScore(score: Int?) {
        if (viewModel.quizMode == QuizMode.TIME) {
            binding.scoreViewTimeAttack.text = score?.toString()
        } else {
            binding.scoreViewNonTime.text = score?.toString()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun startQuiz(view: View) {
        if (viewModel.isQuizRunning) {
            if (viewModel.quizMode == QuizMode.ENDLESS) {
                navigateToResult()
            }
        } else {
            viewModel.startQuiz()
        }
    }

    private fun setChampionsGrid(champions: List<Champion>) {
        binding.btnAns1.setImageResource(
                resources
                        .getIdentifier(
                                champions[0].identifier, "drawable", requireActivity().packageName))
        binding.btnAns2.setImageResource(
                resources
                        .getIdentifier(
                                champions[1].identifier, "drawable", requireActivity().packageName))
        binding.btnAns3.setImageResource(
                resources
                        .getIdentifier(
                                champions[2].identifier, "drawable", requireActivity().packageName))
        binding.btnAns4.setImageResource(
                resources
                        .getIdentifier(
                                champions[3].identifier, "drawable", requireActivity().packageName))
    }

    private fun setTimeAttackLineVisibility(quizMode: QuizMode?) {
        if (quizMode == QuizMode.TIME) {
            binding.timeAttackLayout.visibility = View.VISIBLE
        } else {
            binding.timeAttackLayout.visibility = View.INVISIBLE
        }
    }

    private fun setScoreLineVisibility(quizMode: QuizMode?) {
        if (quizMode == QuizMode.TIME) {
            binding.scoreLineTimeAttack.visibility = View.VISIBLE
            binding.scoreLineNonTime.visibility = View.INVISIBLE
        } else {
            binding.scoreLineTimeAttack.visibility = View.INVISIBLE
            binding.scoreLineNonTime.visibility = View.VISIBLE
        }
    }
}