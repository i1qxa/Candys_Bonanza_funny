package com.nightriders.candylands.ui.fortune_cookies

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentFortuneCookieBinding
import com.nightriders.candylands.domain.BALANS_PREFS
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.LAST_PREDICTION_DAY
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.start.StartFragment

class FortuneCookieFragment : Fragment() {

    private val binding by lazy { FragmentFortuneCookieBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE) }
    private val btnBuy by lazy { binding.btnBuy }
    private val btnFree by lazy { binding.openFree }
    private val listOfPredictions by lazy { listOf(
        requireContext().getString(R.string.prediction_1),
        requireContext().getString(R.string.prediction_2),
        requireContext().getString(R.string.prediction_3),
        requireContext().getString(R.string.prediction_4),
        requireContext().getString(R.string.prediction_5),
        )}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setBtnClickListeners()
    }

    private fun setBtnClickListeners(){
        binding.btnBuy.root.setOnClickListener {
            tryToBuyPrediction()
        }
        binding.openFree.setOnClickListener {
            getFreePrediction()
        }
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun initData(){
        updateBalance()
        val lastDay = prefs.getInt(LAST_PREDICTION_DAY, -1)
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        if (lastDay == currentDay){
            btnFree.visibility = View.GONE
            btnBuy.root.visibility = View.VISIBLE
        }else{
            btnFree.visibility = View.VISIBLE
            btnBuy.root.visibility = View.INVISIBLE
        }
    }

    private fun updateBalance(){
        val balance = prefs.getInt(BALANS_PREFS, 100)
        binding.balance.tvBalanceValue.text = balance.toString()
    }

    private fun getRandomPrediction():String{
        return listOfPredictions.shuffled()[0]
    }

    private fun setNewPrediction(prediction:String){
        binding.prediction.predictionText.text = prediction
        binding.prediction.root.visibility = View.VISIBLE
    }

    private fun tryToBuyPrediction(){
        val balance = prefs.getInt(BALANS_PREFS, 100)
        if (balance>=85){
            setNewPrediction(getRandomPrediction())
            prefs.edit().putInt(BALANS_PREFS, (balance-85)).apply()
            updateBalance()
        }
    }

    private fun getFreePrediction(){
        setNewPrediction(getRandomPrediction())
        prefs.edit().putInt(LAST_PREDICTION_DAY, Calendar.getInstance().get(Calendar.DAY_OF_YEAR)).apply()
        initData()
    }

}