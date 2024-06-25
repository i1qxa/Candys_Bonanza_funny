package com.nightriders.candylands.domain

import android.widget.ImageView

data class GameCandyWithView(
    var view:ImageView,
    var x:Float,
    var y:Float,
){
    var resId:Int? = null
}
