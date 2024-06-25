package com.nightriders.candylands.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentSettingsBinding
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.MUSIC_PREFS
import com.nightriders.candylands.domain.SOUND_PREFS
import com.nightriders.candylands.domain.isMusickOn
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.start.StartFragment

class SettingsFragment : Fragment() {

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitchers()
        setupBtnClickListeners()
    }

    private fun setupBtnClickListeners(){
        binding.musicjSwitcher.setOnClickListener {
            val newValue = !getMusic()
            setupMusicIcon(newValue)
            prefs.edit().putBoolean(MUSIC_PREFS, newValue).apply()
            isMusickOn.isMusicOn.value = newValue
        }
        binding.soundSwitcher.setOnClickListener {
            val newValue = !getSound()
            setupSoundIcon(newValue)
            prefs.edit().putBoolean(SOUND_PREFS, newValue).apply()
        }
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun initSwitchers(){
        setupMusicIcon(getMusic())
        setupSoundIcon(getSound())
    }

    private fun setupMusicIcon(isMusicOn:Boolean){
        val musicIcon = if (isMusicOn) R.drawable.on else R.drawable.off
        binding.musicjSwitcher.setImageResource(musicIcon)
    }

    private fun setupSoundIcon(isSoundOn:Boolean){
        val soundIcon = if (isSoundOn) R.drawable.on else R.drawable.off
        binding.soundSwitcher.setImageResource(soundIcon)
    }

    private fun getMusic():Boolean{
        return prefs.getBoolean(MUSIC_PREFS, true)
    }

    private fun getSound():Boolean{
        return prefs.getBoolean(SOUND_PREFS, true)
    }

}