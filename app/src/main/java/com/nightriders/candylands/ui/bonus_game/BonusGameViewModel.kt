package com.nightriders.candylands.ui.bonus_game

import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nightriders.candylands.domain.GameCandyWithView
import com.nightriders.candylands.domain.listOfBonusGameCandys
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BonusGameViewModel(application: Application):AndroidViewModel(application) {

    var baseScore:Int = 0
    private val listSrcs = listOfBonusGameCandys.shuffled().subList(0, 3)
    private val listCandyWithView = mutableListOf<GameCandyWithView>()
    private var field : Array<Array<GameCandyWithView?>>? = null
    private var multiply = 0
    val multiplyLD = MutableLiveData<Int>(multiply)
    var timer = 8
    val timerLD = MutableLiveData<Int>()
    val shouldFinishGame = MutableLiveData<Int>()
    private var timerCoroutineContext: CoroutineContext? = null


    fun pauseCountDawnTimer(){
        timerCoroutineContext?.let {
            it.cancel()
            timerCoroutineContext = null
        }
    }

    fun startCountDawnTimer(){
        viewModelScope.launch {
            timerCoroutineContext = this.coroutineContext
            while (timer>0){
                delay(1000)
                timer--
                timerLD.postValue(timer)
            }
            shouldFinishGame.postValue(baseScore + ((baseScore.toDouble()*multiply)/10).toInt())
        }
    }

    fun setupSrcs(listView:List<ImageView>){
        listView.forEach {
            listCandyWithView.add(GameCandyWithView(listSrcs.random().resId,it))
        }
        field = arrayOf(
            arrayOf(
                listCandyWithView.get(0),
                listCandyWithView.get(1),
                listCandyWithView.get(2),
                listCandyWithView.get(3),
                listCandyWithView.get(4),
            ),
            arrayOf(
                listCandyWithView.get(5),
                listCandyWithView.get(6),
                listCandyWithView.get(7),
                listCandyWithView.get(8),
                listCandyWithView.get(9),
            ),
            arrayOf(
                listCandyWithView.get(10),
                listCandyWithView.get(11),
                listCandyWithView.get(12),
                listCandyWithView.get(13),
                listCandyWithView.get(14),
            ),
            arrayOf(
                listCandyWithView.get(15),
                listCandyWithView.get(16),
                listCandyWithView.get(17),
                listCandyWithView.get(18),
                listCandyWithView.get(19),
            ),
            arrayOf(
                listCandyWithView.get(20),
                listCandyWithView.get(21),
                listCandyWithView.get(22),
                listCandyWithView.get(23),
                listCandyWithView.get(24),
            ),
            arrayOf(
                listCandyWithView.get(25),
                listCandyWithView.get(26),
                listCandyWithView.get(27),
                listCandyWithView.get(28),
                listCandyWithView.get(29),
            )
        )
        listCandyWithView.forEach { candyWithView ->
            candyWithView.view.setImageResource(candyWithView.resId)
        }
    }

    fun checkRes(column:Int, row:Int){
        val clickedCandy = field!!.get(row)!!.get(column)!!
        //Проверяем строку
        val winRow = mutableListOf<GameCandyWithView>(clickedCandy)
        var left:GameCandyWithView? = clickedCandy
        var right:GameCandyWithView? = clickedCandy
        var leftIndex = column-1
        var rightIndex = column+1
        while (leftIndex>=0&& left!=null){
            if (left.resId == field?.get(row)?.get(leftIndex)?.resId){
                winRow.add(field?.get(row)?.get(leftIndex)!!)
                leftIndex--
            }else left=null
        }
        while (rightIndex<=4&& right!=null){
            if (right.resId == field?.get(row)?.get(rightIndex)?.resId){
                winRow.add(field?.get(row)?.get(rightIndex)!!)
                rightIndex++
            }else right=null
        }
            //Проверяем колонку
        val winColumn = mutableListOf<GameCandyWithView>(clickedCandy)
        var top:GameCandyWithView? = clickedCandy
        var bottom:GameCandyWithView? = clickedCandy
        var topIndex = row-1
        var bottomIndex = row+1
        while (topIndex>=0&& top!=null){
            if (top.resId == field?.get(topIndex)?.get(column)?.resId){
                winColumn.add(field?.get(topIndex)?.get(column)!!)
                topIndex--
            }else top=null
        }
        while (bottomIndex<=5&& bottom!=null){
            if (bottom.resId == field?.get(bottomIndex)?.get(column)?.resId){
                winColumn.add(field?.get(bottomIndex)?.get(column)!!)
                bottomIndex++
            }else bottom=null
        }
        if (winRow.size>2){
            increaseKoeficient()
            winRow.forEach {
                it.view.visibility = View.INVISIBLE
            }
        }
        if (winColumn.size>2){
            increaseKoeficient()
            winColumn.forEach {
                it.view.visibility = View.INVISIBLE
            }
        }
    }

    private fun increaseKoeficient(){
        multiply+=1
        multiplyLD.value = multiply
    }


}