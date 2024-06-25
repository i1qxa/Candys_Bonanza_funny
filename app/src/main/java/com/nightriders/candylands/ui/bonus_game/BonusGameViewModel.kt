package com.nightriders.candylands.ui.bonus_game

import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nightriders.candylands.domain.GameCandyWithView
import com.nightriders.candylands.domain.PositionInField
import com.nightriders.candylands.domain.TIMER_BONUS_GAME
import com.nightriders.candylands.domain.listOfBonusGameCandys
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BonusGameViewModel(application: Application) : AndroidViewModel(application) {

    var baseScore: Int = 0
    val listSrcs = listOfBonusGameCandys.shuffled().subList(0, 3)
    private val listCandyWithView = mutableListOf<GameCandyWithView>()
    lateinit var field: Array<Array<GameCandyWithView>>
    private var multiply = 0
    val multiplyLD = MutableLiveData<Int>(multiply)
    var timer = TIMER_BONUS_GAME
    val timerLD = MutableLiveData<Int>()
    val shouldFinishGame = MutableLiveData<Int>()
    private var timerCoroutineContext: CoroutineContext? = null
    val fieldLD = MutableLiveData<Array<Array<GameCandyWithView>>>()


    fun pauseCountDawnTimer() {
        timerCoroutineContext?.let {
            it.cancel()
            timerCoroutineContext = null
        }
    }

    private fun animVisibility(img:View){
        img.visibility = View.VISIBLE
        img.animate().apply {
            duration = ANIM_DURATION
            scaleX(1F)
            scaleY(1F)
        }
    }

    fun startCountDawnTimer() {
        viewModelScope.launch {
            timerCoroutineContext = this.coroutineContext
            while (timer > 0) {
                delay(1000)
                timer--
                timerLD.postValue(timer)
            }
            shouldFinishGame.postValue(baseScore + ((baseScore.toDouble() * multiply) / 10).toInt())
        }
    }

    fun setupSrcs(listView: List<ImageView>) {
        listView.forEach {
            val newItem = GameCandyWithView(it, it.x, it.y)
            newItem.resId = listSrcs.random().resId
            listCandyWithView.add(newItem)
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
            candyWithView.resId?.let { candyWithView.view.setImageResource(it) }
        }
    }

    fun fullCheckRes():Boolean {
        var isCleaned = false
        var counter = 0
        while (counter < 6) {
            if (field[counter][0].resId == field[counter][1].resId
                && field[counter][0].resId == field[counter][2].resId
                && field[counter][0].resId == field[counter][3].resId
                && field[counter][0].resId == field[counter][4].resId
            ) {
                multiply++
                clearIds(counter, listOf(0, 1, 2, 3, 4))
                isCleaned = true
            }
            if (field[counter][0].resId == field[counter][1].resId
                && field[counter][0].resId == field[counter][2].resId
                && field[counter][0].resId == field[counter][3].resId
            ) {
                multiply++
                clearIds(counter, listOf(0, 1, 2, 3))
                isCleaned = true
            }
            if (field[counter][0].resId == field[counter][1].resId
                && field[counter][0].resId == field[counter][2].resId
            ) {
                multiply++
                clearIds(counter, listOf(0, 1, 2))
                isCleaned = true
            }
            if (field[counter][4].resId == field[counter][3].resId
                && field[counter][0].resId == field[counter][2].resId
                && field[counter][0].resId == field[counter][1].resId
            ) {
                multiply++
                clearIds(counter, listOf(1, 2, 3, 4))
                isCleaned = true
            }
            if (field[counter][4].resId == field[counter][3].resId
                && field[counter][4].resId == field[counter][2].resId
            ) {
                multiply++
                clearIds(counter, listOf(2, 3, 4))
                isCleaned = true
            }
            if (field[counter][1].resId == field[counter][2].resId
                && field[counter][1].resId == field[counter][3].resId
            ) {
                multiply++
                clearIds(counter, listOf(2, 3, 1))
                isCleaned = true
            }
            counter++
        }
        multiplyLD.value = multiply
        fieldLD.value = field
        if(isCleaned) setNewCandys()
        return isCleaned
    }

    fun setNewCandys(){
        viewModelScope.launch {
            delay(ANIM_DURATION)
            field.forEach {
                it.forEach {
                    if (it.resId == null){
                        it.resId = listSrcs.shuffled()[0].resId
                        it.view.setImageResource(it.resId!!)
                        it.view.visibility = View.VISIBLE
                        animVisibility(it.view)
                    }
                }
            }
        }

    }

    private fun clearIds(row: Int, columns: List<Int>) {
        columns.forEach {
            field[row][it].resId = null
        }
    }

    fun checkRes(column: Int, row: Int) {
        val clickedCandy = field!!.get(row)!!.get(column)!!
        //Проверяем строку
        val winRow = mutableListOf<GameCandyWithView>(clickedCandy)
        var left: GameCandyWithView? = clickedCandy
        var right: GameCandyWithView? = clickedCandy
        var leftIndex = column - 1
        var rightIndex = column + 1
        while (leftIndex >= 0 && left != null) {
            if (left.resId == field?.get(row)?.get(leftIndex)?.resId) {
                winRow.add(field?.get(row)?.get(leftIndex)!!)
                leftIndex--
            } else left = null
        }
        while (rightIndex <= 4 && right != null) {
            if (right.resId == field?.get(row)?.get(rightIndex)?.resId) {
                winRow.add(field?.get(row)?.get(rightIndex)!!)
                rightIndex++
            } else right = null
        }
        //Проверяем колонку
        val winColumn = mutableListOf<GameCandyWithView>(clickedCandy)
        var top: GameCandyWithView? = clickedCandy
        var bottom: GameCandyWithView? = clickedCandy
        var topIndex = row - 1
        var bottomIndex = row + 1
        while (topIndex >= 0 && top != null) {
            if (top.resId == field?.get(topIndex)?.get(column)?.resId) {
                winColumn.add(field?.get(topIndex)?.get(column)!!)
                topIndex--
            } else top = null
        }
        while (bottomIndex <= 5 && bottom != null) {
            if (bottom.resId == field?.get(bottomIndex)?.get(column)?.resId) {
                winColumn.add(field?.get(bottomIndex)?.get(column)!!)
                bottomIndex++
            } else bottom = null
        }
        if (winRow.size > 2) {
            increaseKoeficient()
            winRow.forEach {
                it.view.visibility = View.INVISIBLE
            }
        }
        if (winColumn.size > 2) {
            increaseKoeficient()
            winColumn.forEach {
                it.view.visibility = View.INVISIBLE
            }
        }
    }

    private fun increaseKoeficient() {
        multiply += 1
        multiplyLD.value = multiply
    }

    fun moveCardInField(
        firstField: PositionInField,
        secondField: PositionInField,
        firstCandyWithView: GameCandyWithView,
        secondCandyWithView: GameCandyWithView
    ) {
        val newFieldAsList = mutableListOf<GameCandyWithView>()
        var row = 0
        var column = 0
        while (row <= 5) {
            if (row == firstField.row && column == firstField.column) {
                newFieldAsList.add(secondCandyWithView)
            } else if (row == secondField.row && column == secondField.column) {
                newFieldAsList.add(firstCandyWithView)
            } else {
                field?.get(row)?.get(column)?.let { newFieldAsList.add(it) }
            }
            column++
            if (column > 4) {
                column = 0
                row++
            }
        }
        val newField = arrayOf(
            arrayOf(
                newFieldAsList[0],
                newFieldAsList[1],
                newFieldAsList[2],
                newFieldAsList[3],
                newFieldAsList[4],
            ),
            arrayOf(
                newFieldAsList[5],
                newFieldAsList[6],
                newFieldAsList[7],
                newFieldAsList[8],
                newFieldAsList[9],
            ),
            arrayOf(
                newFieldAsList[10],
                newFieldAsList[11],
                newFieldAsList[12],
                newFieldAsList[13],
                newFieldAsList[14],
            ),
            arrayOf(
                newFieldAsList[15],
                newFieldAsList[16],
                newFieldAsList[17],
                newFieldAsList[18],
                newFieldAsList[19],
            ),
            arrayOf(
                newFieldAsList[20],
                newFieldAsList[21],
                newFieldAsList[22],
                newFieldAsList[23],
                newFieldAsList[24],
            ),
            arrayOf(
                newFieldAsList[25],
                newFieldAsList[26],
                newFieldAsList[27],
                newFieldAsList[28],
                newFieldAsList[29],
            )
        )
        newField.also { field = it }
    }


}