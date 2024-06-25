package com.nightriders.candylands.ui.lvl_failed

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentLvlFailedBinding
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.SOUND_PREFS
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.game.GameFragment
import com.nightriders.candylands.ui.start.StartFragment

class LvlFailedFragment : Fragment() {

    private val binding by lazy { FragmentLvlFailedBinding.inflate(layoutInflater) }
    private var mMediaPlayer: MediaPlayer? = null
    private val isSoundOn by lazy { requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE).getBoolean(
        SOUND_PREFS, true) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
        binding.tvContinue.setOnClickListener {
            parentFragmentManager.launchFragment(GameFragment())
        }
        if (isSoundOn){
            playSound()
        }
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.lose)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

}