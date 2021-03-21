package com.scp.leagueofquiz.entrypoint.itemquiz

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.databinding.ItemQuizFragmentBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ItemQuizFragment : Fragment(R.layout.item_quiz_fragment) {
    private val mViewModel: ItemQuizViewModel by viewModels()
    private val binding by viewBinding(ItemQuizFragmentBinding::bind)
}