package com.nightriders.candylands.ui.choose_level.rv

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nightriders.candylands.R

class LevelViewHolder(item:View):RecyclerView.ViewHolder(item) {

    val lvl = item.findViewById<TextView>(R.id.tvLevelValue)

}