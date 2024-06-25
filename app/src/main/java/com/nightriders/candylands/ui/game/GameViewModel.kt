package com.nightriders.candylands.ui.game

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.GameCandy
import com.nightriders.candylands.domain.LAST_COMPLETE_LEVEL_PREF
import com.nightriders.candylands.domain.SelectedCandy
import com.nightriders.candylands.domain.TIMER_MAIN_GAME
import com.nightriders.candylands.domain.isGameOnPause
import com.nightriders.candylands.domain.listOfGameCandys
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs =
        application.baseContext.getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE)
    private val level = prefs.getInt(LAST_COMPLETE_LEVEL_PREF, 1)
    var endless = false
    var score = 0
    var timer = TIMER_MAIN_GAME
    var round = 1
    var roundMax = when (level) {
        in 0..15 -> 3
        in 16..30 -> 4
        else -> 5
    }
    val shouldUpdateVisibility = MutableLiveData<Boolean>(false)
    val currentRound = MutableLiveData<Int>(round)
    val timerLD = MutableLiveData<Int>(8)
    val topCandysLD = MutableLiveData<List<GameCandy>>()
    val bottomCandysLD = MutableLiveData<List<GameCandy>>()
    var topCandy: SelectedCandy? = null
    var bottomCandy: SelectedCandy? = null
    val shouldFinishGame = MutableLiveData<Int>()
    private var timerCoroutineContext: CoroutineContext? = null

    fun finishRound() {
        round++
        if (round <= roundMax) {
            currentRound.value = round
            startRound()
        } else shouldFinishGame.value = score

    }

    fun finishRoundEndless() {
        round++
        currentRound.value = round
        startRound()
    }

    fun pauseCountDawnTimer() {
        timerCoroutineContext?.let {
            it.cancel()
            timerCoroutineContext = null
        }
    }

    fun startCountDawnTimer() {
        viewModelScope.launch {
            isGameOnPause.isGameOnPauseLD.postValue(null)
            timerCoroutineContext = this.coroutineContext
            while (timer > 0) {
                delay(1000)
                timer--
                timerLD.postValue(timer)
            }
            if (endless) finishRoundEndless() else finishRound()
            this.coroutineContext.cancel()
        }
    }

    fun startRound() {
        shouldUpdateVisibility.value = true
        fetchCandysList()
        shouldUpdateVisibility.value = false
        timer = TIMER_MAIN_GAME
        timerLD.postValue(timer)
        startCountDawnTimer()
    }

    fun fetchCandysList() {
        topCandysLD.value = listOfGameCandys.shuffled().subList(0, 7)
        bottomCandysLD.value = listOfGameCandys.shuffled().subList(0, 7)
    }

    fun setupTopCandy(view: View, position: Int) {
        topCandy = SelectedCandy(view, topCandysLD.value?.get(position)?.resId ?: 1)
        checkRes()
    }

    fun setupBottomCandy(view: View, position: Int) {
        bottomCandy = SelectedCandy(view, bottomCandysLD.value?.get(position)?.resId ?: 1)
        checkRes()
    }

    private fun checkRes() {
        if (topCandy != null && bottomCandy != null) {
            if (topCandy?.resId == bottomCandy?.resId) {
                topCandy?.view?.visibility = View.INVISIBLE
                bottomCandy?.view?.visibility = View.INVISIBLE
                score++
            }
            topCandy = null
            bottomCandy = null
        }
    }

    init {
        startRound()
    }

}