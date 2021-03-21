package com.scp.leagueofquiz.entrypoint.abilityquiz

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.databinding.AbilityQuizFragmentBinding
import com.scp.leagueofquiz.entrypoint.quizresult.QuizResultViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class AbilityQuizFragment : Fragment(R.layout.ability_quiz_fragment) {
    private val viewModel: QuizResultViewModel by viewModels()
    private val binding by viewBinding(AbilityQuizFragmentBinding::bind)
}