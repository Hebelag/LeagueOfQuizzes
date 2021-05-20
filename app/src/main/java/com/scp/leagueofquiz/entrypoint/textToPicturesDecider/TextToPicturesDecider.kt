package com.scp.leagueofquiz.entrypoint.textToPicturesDecider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.databinding.TextToPicturesDeciderFragmentBinding
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextToPicturesDecider : Fragment(R.layout.text_to_pictures_decider_fragment) {
    private val viewModel: TextToPicturesDeciderViewModel by viewModels()
    private val binding by viewBinding(TextToPicturesDeciderFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToChampions.setOnClickListener{
            NavHostFragment.findNavController(this)
                .navigate(TextToPicturesDeciderDirections.goToTextToPictures(QuizType.CHAMPION))
        }
        binding.buttonToAbilitites.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(TextToPicturesDeciderDirections.goToTextToPictures(QuizType.ABILITY))
        }
        binding.buttonToIcons.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(TextToPicturesDeciderDirections.goToTextToPictures(QuizType.ITEM))
        }

    }

}