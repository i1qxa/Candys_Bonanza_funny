package com.nightriders.candylands.ui.bonus_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentBonusGameBinding
import com.nightriders.candylands.domain.isGameOnPause
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.help.HelpFragment
import com.nightriders.candylands.ui.lvl_complete.LvlCompleteFragment
import com.nightriders.candylands.ui.lvl_failed.LvlFailedFragment
import com.nightriders.candylands.ui.pause.PauseFragment
import com.nightriders.candylands.ui.start.StartFragment

private const val BASE_SCORE = "base_score"


class BonusGameFragment : Fragment() {
    private val binding by lazy { FragmentBonusGameBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<BonusGameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.baseScore = it.getInt(BASE_SCORE)
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
        initBonusGame()
        setupCandysClickListener()
        observeKoeficient()
        observeTimer()
        shouldFinishGame()
        observeGameState()
        setupBtnClickListeners()
        viewModel.startCountDawnTimer()
    }

    private fun setupBtnClickListeners() {
        setupBtnHome()
        setupBtnPause()
        setupBtnHelp()
    }

    private fun setupBtnHome() {
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun setupBtnPause() {
        binding.btnPause.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.postValue(true)
            parentFragmentManager.beginTransaction().apply {
                add(R.id.mainContainer, PauseFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeGameState() {
        isGameOnPause.isGameOnPauseLD.observe(viewLifecycleOwner) { isGameOnPause ->
            if (isGameOnPause == true) {
                viewModel.pauseCountDawnTimer()
            } else if(isGameOnPause==false){
                viewModel.startCountDawnTimer()
            }
        }
    }

    private fun setupBtnHelp() {
        binding.btnHelp.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = true
            parentFragmentManager.beginTransaction().apply {
                add(R.id.mainContainer, HelpFragment.newInstance(false))
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun shouldFinishGame() {
        viewModel.shouldFinishGame.observe(viewLifecycleOwner) { score ->
            if (score > 0) {
                parentFragmentManager.launchFragment(LvlCompleteFragment.newInstance(score))
            }else{
                parentFragmentManager.launchFragment(LvlFailedFragment())
            }
        }
    }

    private fun observeTimer() {
        viewModel.timerLD.observe(viewLifecycleOwner) {
            binding.pbTimer.setProgress(it, true)
        }
    }

    private fun observeKoeficient() {
        viewModel.multiplyLD.observe(viewLifecycleOwner) {
            binding.tvKoeficient.text = getString(R.string.koeficient, it.toString())
        }
    }

    private fun initBonusGame() {
        viewModel.setupSrcs(
            listOf(
                binding.bonus11,
                binding.bonus12,
                binding.bonus13,
                binding.bonus14,
                binding.bonus15,
                binding.bonus21,
                binding.bonus22,
                binding.bonus23,
                binding.bonus24,
                binding.bonus25,
                binding.bonus31,
                binding.bonus32,
                binding.bonus33,
                binding.bonus34,
                binding.bonus35,
                binding.bonus41,
                binding.bonus42,
                binding.bonus43,
                binding.bonus44,
                binding.bonus45,
                binding.bonus51,
                binding.bonus52,
                binding.bonus53,
                binding.bonus54,
                binding.bonus55,
                binding.bonus61,
                binding.bonus62,
                binding.bonus63,
                binding.bonus64,
                binding.bonus65,
            )
        )
    }

    private fun setupCandysClickListener() {
        with(binding) {
            bonus11.setOnClickListener {
                viewModel.checkRes(0, 0)
            }
            bonus12.setOnClickListener {
                viewModel.checkRes(1, 0)
            }
            bonus13.setOnClickListener {
                viewModel.checkRes(2, 0)
            }
            bonus14.setOnClickListener {
                viewModel.checkRes(3, 0)
            }
            bonus15.setOnClickListener {
                viewModel.checkRes(4, 0)
            }
            bonus21.setOnClickListener {
                viewModel.checkRes(0, 1)
            }
            bonus22.setOnClickListener {
                viewModel.checkRes(1, 1)
            }
            bonus23.setOnClickListener {
                viewModel.checkRes(2, 1)
            }
            bonus24.setOnClickListener {
                viewModel.checkRes(3, 1)
            }
            bonus25.setOnClickListener {
                viewModel.checkRes(4, 1)
            }
            bonus31.setOnClickListener {
                viewModel.checkRes(0, 2)
            }
            bonus32.setOnClickListener {
                viewModel.checkRes(1, 2)
            }
            bonus33.setOnClickListener {
                viewModel.checkRes(2, 2)
            }
            bonus34.setOnClickListener {
                viewModel.checkRes(3, 2)
            }
            bonus35.setOnClickListener {
                viewModel.checkRes(4, 2)
            }
            bonus14.setOnClickListener {
                viewModel.checkRes(0, 3)
            }
            bonus42.setOnClickListener {
                viewModel.checkRes(1, 3)
            }
            bonus43.setOnClickListener {
                viewModel.checkRes(2, 3)
            }
            bonus44.setOnClickListener {
                viewModel.checkRes(3, 3)
            }
            bonus45.setOnClickListener {
                viewModel.checkRes(4, 3)
            }
            bonus51.setOnClickListener {
                viewModel.checkRes(0, 4)
            }
            bonus52.setOnClickListener {
                viewModel.checkRes(1, 4)
            }
            bonus53.setOnClickListener {
                viewModel.checkRes(2, 4)
            }
            bonus54.setOnClickListener {
                viewModel.checkRes(3, 4)
            }
            bonus55.setOnClickListener {
                viewModel.checkRes(4, 4)
            }
            bonus61.setOnClickListener {
                viewModel.checkRes(0, 5)
            }
            bonus62.setOnClickListener {
                viewModel.checkRes(1, 5)
            }
            bonus63.setOnClickListener {
                viewModel.checkRes(2, 5)
            }
            bonus64.setOnClickListener {
                viewModel.checkRes(3, 5)
            }
            bonus65.setOnClickListener {
                viewModel.checkRes(4, 5)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(baseScore: Int) =
            BonusGameFragment().apply {
                arguments = Bundle().apply {
                    putInt(BASE_SCORE, baseScore)
                }
            }
    }
}