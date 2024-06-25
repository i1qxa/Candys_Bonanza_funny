package com.nightriders.candylands.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightriders.candylands.domain.LOADING_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {


    private val _shouldLaunchGame = MutableLiveData<Boolean>()
    val shouldLaunchGame:LiveData<Boolean>
        get() = _shouldLaunchGame
    private val _progressLD = MutableLiveData<Int>(0)
    val progressLD:LiveData<Int>
        get() = _progressLD
    init {
        viewModelScope.launch {
            var counter = 0
            while (counter<100){
                delay(LOADING_DELAY)
                counter+=1
                _progressLD.postValue(counter)
            }
            if (counter>=100){
                _shouldLaunchGame.postValue(true)
            }
        }
    }
}