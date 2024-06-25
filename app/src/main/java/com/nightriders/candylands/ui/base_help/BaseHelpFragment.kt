package com.nightriders.candylands.ui.base_help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentBaseHelpBinding
import com.nightriders.candylands.domain.Tip
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.start.StartFragment

class BaseHelpFragment : Fragment() {

    private val binding by lazy{FragmentBaseHelpBinding.inflate(layoutInflater)}
    private var currentTip = 0
    private val listOfTips by lazy { listOf(
        Tip(R.drawable.phone1, requireContext().getString(R.string.tip_1)),
        Tip(R.drawable.phone2, requireContext().getString(R.string.tip_2)),
        Tip(R.drawable.phone3, requireContext().getString(R.string.tip_3))
    ) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnClickListeners()
    }

    private fun setupBtnClickListeners(){

        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }

        binding.btnNext.setOnClickListener {
            when(currentTip){
                0 -> {
                    binding.tipImg.setImageResource(listOfTips[1].imgId)
                    binding.tipText.text = listOfTips[1].tip
                    currentTip = 1
                }
                1 -> {
                    binding.tipImg.setImageResource(listOfTips[2].imgId)
                    binding.tipText.text = listOfTips[2].tip
                    currentTip = 2
                }
                else -> {
                    parentFragmentManager.launchFragment(StartFragment())
                }
            }
        }

    }

}