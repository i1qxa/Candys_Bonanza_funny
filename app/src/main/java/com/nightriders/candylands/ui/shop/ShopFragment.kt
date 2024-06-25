package com.nightriders.candylands.ui.shop

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentShopBinding
import com.nightriders.candylands.domain.BALANS_PREFS
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.SHUFFLE_AMOUNT
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.start.StartFragment

class ShopFragment : Fragment() {

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val prefs by lazy {
        requireContext().getSharedPreferences(
            CANDYS_PREFS,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupValues()
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.priceAr.root.setOnClickListener {
            if (getBalance() > 85) {
                updateBalance()
                updateShuffle()
                setupValues()
            }
        }
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun setupValues() {
        binding.balance.tvBalanceValue.text = getBalance().toString()
        binding.shuffleAmount.text = getShuffleAmount().toString()
    }

    private fun getBalance(): Int {
        return prefs.getInt(BALANS_PREFS, 100)
    }

    private fun getShuffleAmount(): Int {
        return prefs.getInt(SHUFFLE_AMOUNT, 0)
    }

    private fun updateBalance() {
        val balance = getBalance()
        prefs.edit().putInt(BALANS_PREFS, (balance - 85)).apply()
    }

    private fun updateShuffle() {
        val shuffle = getShuffleAmount()
        prefs.edit().putInt(SHUFFLE_AMOUNT, (shuffle + 1)).apply()
    }

}