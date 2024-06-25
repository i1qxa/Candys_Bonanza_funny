package com.nightriders.candylands.ui.start

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.replace
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentStartBinding
import com.nightriders.candylands.domain.BALANS_PREFS
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.base_help.BaseHelpFragment
import com.nightriders.candylands.ui.bonus_game.BonusGameFragment
import com.nightriders.candylands.ui.choose_level.ChooseLevelFragment
import com.nightriders.candylands.ui.fortune_cookies.FortuneCookieFragment
import com.nightriders.candylands.ui.game.GameFragment
import com.nightriders.candylands.ui.settings.SettingsFragment
import com.nightriders.candylands.ui.shop.ShopFragment

class StartFragment : Fragment() {

    private val viewModel: StartViewModel by viewModels()
    private val binding by lazy { FragmentStartBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnClickListeners()
        setupBalance()
    }

    private fun setupBalance(){
        val balance = prefs.getInt(BALANS_PREFS, 100)
        binding.balance.tvBalanceValue.text = balance.toString()
    }

    private fun setupBtnClickListeners(){
        binding.btnPlay.setOnClickListener {
            parentFragmentManager.launchFragment(ChooseLevelFragment())
        }
        binding.btnSettings.setOnClickListener {
            parentFragmentManager.launchFragment(SettingsFragment())
        }
        binding.btnEndless.setOnClickListener {
            parentFragmentManager.launchFragment(GameFragment.newInstance(true))
        }
        binding.btnShop.setOnClickListener {
            parentFragmentManager.launchFragment(ShopFragment())
        }
        binding.btnFortuneCookie.setOnClickListener {
            parentFragmentManager.launchFragment(FortuneCookieFragment())
        }
        binding.btnHelp.setOnClickListener {
            parentFragmentManager.launchFragment(BaseHelpFragment())
        }
    }


}