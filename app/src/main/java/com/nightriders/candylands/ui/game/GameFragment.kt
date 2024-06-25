package com.nightriders.candylands.ui.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentGameBinding
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.SHUFFLE_AMOUNT
import com.nightriders.candylands.domain.isGameOnPause
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.bonus_game.BonusGameFragment
import com.nightriders.candylands.ui.help.HelpFragment
import com.nightriders.candylands.ui.lvl_complete.LvlCompleteFragment
import com.nightriders.candylands.ui.pause.PauseFragment
import com.nightriders.candylands.ui.start.StartFragment
import kotlin.random.Random

private const val ENDLESS = "endless"

class GameFragment : Fragment() {

    private var endless: Boolean = false
    private val viewModel: GameViewModel by viewModels()
    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endless = it.getBoolean(ENDLESS)
        }
        viewModel.fetchCandysList()
        viewModel.endless = endless
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCandysList()
        observeCandysVisible()
        observeTimer()
        observeRound()
        shouldFinishGame()
        setupBtnClickListeners()
        observeGameState()
        setupShuffleAmount()
    }

    private fun shuffle(){
        val amount = getShuffleAmount()
        if (amount>0){
            prefs.edit().putInt(SHUFFLE_AMOUNT, (amount-1)).apply()
            setupShuffleAmount()
            viewModel.shouldUpdateVisibility.value = true
            viewModel.fetchCandysList()
            viewModel.shouldUpdateVisibility.value = false
        }
    }

    private fun setupShuffleAmount(){
        binding.shuffleAmount.text = getShuffleAmount().toString()
    }

    private fun getShuffleAmount():Int{
        return prefs.getInt(SHUFFLE_AMOUNT, 0)
    }


    private fun setupBtnClickListeners() {
        setupBtnHome()
        setupBtnPause()
        setupBtnHelp()
        binding.btnShuffle.setOnClickListener {
            shuffle()
        }
    }

    private fun setupBtnHome() {
        binding.btnHome.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = null
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun setupBtnPause(){
        binding.btnPause.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.postValue(true)
            parentFragmentManager.beginTransaction().apply {
                add(R.id.mainContainer, PauseFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeGameState(){
        isGameOnPause.isGameOnPauseLD.observe(viewLifecycleOwner){ isGameOnPause ->
            if (isGameOnPause == true){
                viewModel.pauseCountDawnTimer()
            }else if (isGameOnPause==false){
                viewModel.startCountDawnTimer()
            }
        }
    }

    private fun setupBtnHelp(){
        binding.btnHelp.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = true
            parentFragmentManager.beginTransaction().apply {
                add(R.id.mainContainer, HelpFragment.newInstance(true))
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun shouldFinishGame() {
        viewModel.shouldFinishGame.observe(viewLifecycleOwner) {
            parentFragmentManager.launchFragment(BonusGameFragment.newInstance(it))
        }
    }

    private fun observeTimer() {
        viewModel.timerLD.observe(viewLifecycleOwner) {
            binding.pbTimer.setProgress(it, true)
            binding.tvTimer.text = it.toString()
        }
    }

    private fun observeRound() {
        viewModel.currentRound.observe(viewLifecycleOwner) {
            binding.tvRound.text = getString(R.string.round, it.toString())
        }
    }

    private fun observeCandysList() {
        viewModel.topCandysLD.observe(viewLifecycleOwner) { candies ->
            with(binding.topRound) {
                setNewRes(candy11, candies[0].resId)
                setNewRes(candy12, candies[1].resId)
                setNewRes(candy21, candies[2].resId)
                setNewRes(candy22, candies[3].resId)
                setNewRes(candy23, candies[4].resId)
                setNewRes(candy31, candies[5].resId)
                setNewRes(candy32, candies[6].resId)
                candy11.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 0)
                }
                candy12.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 1)
                }
                candy21.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 2)
                }
                candy22.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 3)
                }
                candy23.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 4)
                }
                candy31.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 5)
                }
                candy32.setOnClickListener {
                    setCandyBckgr(it as ImageView, true)
                    viewModel.setupTopCandy(it, 6)
                }
            }

        }
        viewModel.bottomCandysLD.observe(viewLifecycleOwner) { candies ->
            with(binding.bottomRound) {
                setNewRes(candy11, candies[0].resId)
                setNewRes(candy12, candies[1].resId)
                setNewRes(candy21, candies[2].resId)
                setNewRes(candy22, candies[3].resId)
                setNewRes(candy23, candies[4].resId)
                setNewRes(candy31, candies[5].resId)
                setNewRes(candy32, candies[6].resId)
                candy11.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 0)
                }
                candy12.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 1)
                }
                candy21.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 2)
                }
                candy22.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 3)
                }
                candy23.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 4)
                }
                candy31.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 5)
                }
                candy32.setOnClickListener {
                    setCandyBckgr(it as ImageView, false)
                    viewModel.setupBottomCandy(it, 6)
                }
            }
        }
    }

    private fun setCandyBckgr(candy: ImageView, isTopRound: Boolean) {
        clearCandyBackground(isTopRound)
        candy.setBackgroundResource(R.drawable.lvl_green_bckgr)
    }

    private fun clearCandyBackground(isTopRound: Boolean) {
        if (isTopRound) {
            with(binding.topRound) {
                candy11.background = null
                candy12.background = null
                candy21.background = null
                candy22.background = null
                candy23.background = null
                candy31.background = null
                candy32.background = null
            }
        } else {
            with(binding.bottomRound) {
                candy11.background = null
                candy12.background = null
                candy21.background = null
                candy22.background = null
                candy23.background = null
                candy31.background = null
                candy32.background = null
            }
        }
    }

    private fun observeCandysVisible() {
        viewModel.shouldUpdateVisibility.observe(viewLifecycleOwner) {
            if (it) {
                clearCandyBackground(true)
                clearCandyBackground(false)
                with(binding.topRound) {
                    candy11.visibility = View.VISIBLE
                    candy12.visibility = View.VISIBLE
                    candy21.visibility = View.VISIBLE
                    candy22.visibility = View.VISIBLE
                    candy23.visibility = View.VISIBLE
                    candy31.visibility = View.VISIBLE
                    candy32.visibility = View.VISIBLE
                }
                with(binding.bottomRound) {
                    candy11.visibility = View.VISIBLE
                    candy12.visibility = View.VISIBLE
                    candy21.visibility = View.VISIBLE
                    candy22.visibility = View.VISIBLE
                    candy23.visibility = View.VISIBLE
                    candy31.visibility = View.VISIBLE
                    candy32.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setNewRes(candy: ImageView, resId: Int) {
        candy.setImageResource(resId)
        candy.rotation = Random.nextFloat() * 200
    }

    companion object {
        @JvmStatic
        fun newInstance(endless: Boolean) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ENDLESS, endless)
                }
            }
    }

}