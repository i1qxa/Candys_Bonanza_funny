package com.nightriders.candylands.domain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.lifecycle.MutableLiveData
import com.nightriders.candylands.R

const val LOADING_DELAY:Long = 30
const val CANDYS_PREFS = "candys_prefs"
const val LAST_COMPLETE_LEVEL_PREF = "last_complete_level"
const val AMOUNT_OF_LEVEL = 40
const val BALANS_PREFS = "balance_prefs"
const val MUSIC_PREFS = "music_prefs"
const val SOUND_PREFS = "sound_prefs"
const val SHUFFLE_AMOUNT = "shuffle_amount"
const val LAST_PREDICTION_DAY = "last_prediction_day"
const val TIMER_MAIN_GAME = 10
const val TIMER_BONUS_GAME = 30

enum class MoveDirections(){
    TOP,
    DAWN,
    RIGHT,
    LEFT
}

data class Coordinate(
    val x:Float,
    val y:Float,
)

data class PositionInField(
    val viewId:Int,
    val column:Int,
    val row:Int,
    val coordinate:Coordinate
)

val listOfGameCandys = listOf(
    GameCandy(1,R.drawable.candy1),
    GameCandy(2,R.drawable.candy2),
    GameCandy(3,R.drawable.candy3),
    GameCandy(4,R.drawable.candy4),
    GameCandy(5,R.drawable.candy5),
    GameCandy(6,R.drawable.candy6),
    GameCandy(7,R.drawable.candy7),
    GameCandy(8,R.drawable.candy8),
    GameCandy(9,R.drawable.candy9),
)

val listOfBonusGameCandys = listOf(
    GameCandy(1,R.drawable.bonus1),
    GameCandy(2,R.drawable.bonus2),
    GameCandy(3,R.drawable.bonus3),
    GameCandy(4,R.drawable.bonus4),
    GameCandy(5,R.drawable.bonus5),
    GameCandy(6,R.drawable.bonus6),
    GameCandy(7,R.drawable.bonus7),
    GameCandy(8,R.drawable.bonus8),
    GameCandy(9,R.drawable.bonus9),
)

fun FragmentManager.launchFragment(fragment:Fragment){
    this.beginTransaction().apply {
        replace(R.id.mainContainer, fragment)
        addToBackStack(null)
        commit()
    }
}

object isGameOnPause{
    val isGameOnPauseLD = MutableLiveData<Boolean?>()
}

object isMusickOn{
    val isMusicOn = MutableLiveData<Boolean>()
}