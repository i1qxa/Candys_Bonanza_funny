package com.nightriders.candylands.ui.choose_level

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nightriders.candylands.domain.AMOUNT_OF_LEVEL
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.LAST_COMPLETE_LEVEL_PREF
import com.nightriders.candylands.domain.Level

class ChooseLevelViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs =
        application.baseContext.getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE)
    private val lastCompleteLevel = prefs.getInt(LAST_COMPLETE_LEVEL_PREF, 1)
    private val _listLvlLd = MutableLiveData<List<Level>>(getLevelList())
    val listLvlsLD:LiveData<List<Level>>
        get() = _listLvlLd

    private fun getLevelList(): List<Level> {
        val listLvls = mutableListOf<Level>()
        var counter = 1
        if (lastCompleteLevel > 0) {
            while (counter <= lastCompleteLevel) {
                listLvls.add(Level(counter, true))
                counter++
            }
        }
        while (counter <= AMOUNT_OF_LEVEL) {
            listLvls.add(Level(counter, false))
            counter++
        }
        return listLvls
    }

}