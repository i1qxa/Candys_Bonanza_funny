package com.nightriders.candylands.ui.choose_level

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentChooseLevelBinding
import com.nightriders.candylands.domain.BALANS_PREFS
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.choose_level.rv.LevelRVAdapter
import com.nightriders.candylands.ui.game.GameFragment
import com.nightriders.candylands.ui.start.StartFragment

class ChooseLevelFragment : Fragment() {

    private val binding by lazy { FragmentChooseLevelBinding.inflate(layoutInflater) }
    private val rvLevels by lazy { binding.rvLevels }
    private val rvAdapter by lazy { LevelRVAdapter() }

    private val viewModel: ChooseLevelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeLvls()
        setupBtnHomeClickListener()
        initBalance()
    }

    private fun setupBtnHomeClickListener(){
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun initBalance(){
        val balance = requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE).getInt(
            BALANS_PREFS, 100)
        binding.balance.tvBalanceValue.text = balance.toString()
    }

    private fun setupRVAdapter() {
        rvAdapter.onLvlClickListener = {
            if (it.isComplete) {
                parentFragmentManager.launchFragment(GameFragment())
            }
        }
    }

    private fun observeLvls() {
        viewModel.listLvlsLD.observe(viewLifecycleOwner) {
            rvAdapter.submitList(it)
        }
    }

    private fun setupRV() {
        setupRVAdapter()
        with(rvLevels) {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                3,
            )
        }
    }

}