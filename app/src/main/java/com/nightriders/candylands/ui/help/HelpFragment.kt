package com.nightriders.candylands.ui.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentHelpBinding
import com.nightriders.candylands.domain.isGameOnPause

private const val IS_MAIN_GAME = "is_main_game"

class HelpFragment : Fragment() {
    private var isMainGmae: Boolean = true
    private val binding by lazy { FragmentHelpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isMainGmae = it.getBoolean(IS_MAIN_GAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupBtnClickListener()
    }

    private fun setupBtnClickListener(){
        binding.btnOkay.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = false
            parentFragmentManager.popBackStack()
        }
        binding.pauseBckgr.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = false
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupData(){
        if (isMainGmae){
            binding.tvRulesText.setText(R.string.game_rules)
            binding.img.setImageResource(R.drawable.game_rules)
        }else{
            binding.tvRulesText.setText(R.string.bonus_game_rules)
            binding.img.setImageResource(R.drawable.bonus_game_rules)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isMainGame: Boolean) =
            HelpFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_MAIN_GAME, isMainGame)
                }
            }
    }
}