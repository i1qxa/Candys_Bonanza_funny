package com.nightriders.candylands.ui.lvl_complete

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentLvlCompleteBinding
import com.nightriders.candylands.domain.BALANS_PREFS
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.LAST_COMPLETE_LEVEL_PREF
import com.nightriders.candylands.domain.SOUND_PREFS
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.choose_level.ChooseLevelFragment
import com.nightriders.candylands.ui.start.StartFragment

private const val SCORE = "score"
class LvlCompleteFragment : Fragment() {

    private var score: Int = 1
    private val binding by lazy { FragmentLvlCompleteBinding.inflate(layoutInflater) }
    private var mMediaPlayer: MediaPlayer? = null
    private val isSoundOn by lazy { requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE).getBoolean(
        SOUND_PREFS, true) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(SCORE)
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
        setupBalance()
        setupBtnClickListeners()
        if (isSoundOn){
            playSound()
        }
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.win_res)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun setupBalance(){
        binding.tvScore.text = score.toString()
        val prefs = requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE)
        val balance = prefs.getInt(BALANS_PREFS, 100)
        val lvl = prefs.getInt(LAST_COMPLETE_LEVEL_PREF, 1)
        prefs.edit().putInt(BALANS_PREFS, (balance+score)).apply()
        prefs.edit().putInt(LAST_COMPLETE_LEVEL_PREF, (lvl+1)).apply()
    }

    private fun setupBtnClickListeners(){
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
        binding.tvContinue.setOnClickListener {
            parentFragmentManager.launchFragment(ChooseLevelFragment())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(score: Int) =
            LvlCompleteFragment().apply {
                arguments = Bundle().apply {
                    putInt(SCORE, score)
                }
            }
    }
}