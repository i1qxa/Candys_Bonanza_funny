package com.nightriders.candylands.ui.pause

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentPauseBinding
import com.nightriders.candylands.domain.isGameOnPause
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.start.StartFragment

class PauseFragment : Fragment() {

    private val binding by lazy { FragmentPauseBinding.inflate(layoutInflater) }

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

    private fun setupBtnClickListeners() {
        setupBtnHome()
        setupBtnContinue()
    }

    private fun setupBtnHome() {
        binding.btnHome.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = false
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun setupBtnContinue() {
        binding.tvContinue.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = false
            parentFragmentManager.popBackStack()
        }
    }

}