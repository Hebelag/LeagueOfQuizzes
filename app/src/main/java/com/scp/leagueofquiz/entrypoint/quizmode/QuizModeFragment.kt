package com.scp.leagueofquiz.entrypoint.quizmode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.databinding.QuizModeFragmentBinding
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class QuizModeFragment : Fragment(R.layout.quiz_mode_fragment) {
    private val viewModel: QuizModeViewModel by viewModels()
    private val binding by viewBinding(QuizModeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*

        binding.trainFirstButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING)
            action.champCount = 20
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.trainSecondButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING)
            action.champCount = 50
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.trainThirdButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING)
            action.champCount = 100
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.timeFirstButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TIME)
            action.time = 30000
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.timeSecondButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TIME)
            action.time = 60000
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.timeThirdButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TIME)
            action.time = 120000
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.endlessButton.setOnClickListener {
            val action = QuizModeFragmentDirections.startChampQuiz(QuizMode.ENDLESS)
            action.champCount = Int.MAX_VALUE
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.marathonButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(QuizModeFragmentDirections.startChampQuiz(QuizMode.MARATHON))
        }
        */
    }
}