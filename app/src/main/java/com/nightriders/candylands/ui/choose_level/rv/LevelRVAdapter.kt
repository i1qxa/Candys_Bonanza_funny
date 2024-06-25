package com.nightriders.candylands.ui.choose_level.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nightriders.candylands.R
import com.nightriders.candylands.domain.Level

const val LVL_NOT_COMPLETE = 0
const val LVL_COMPLETE = 1

class LevelRVAdapter : ListAdapter<Level, LevelViewHolder>(LevelDiffCallback()) {

    var onLvlClickListener: ((Level) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layout =
            if (viewType == LVL_COMPLETE) R.layout.rv_level_item_complete
            else R.layout.rv_level_item_not_complete
        return LevelViewHolder(
            layoutInflater.inflate(
                layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val item = getItem(position)
        holder.lvl.text = item.lvl.toString()
        holder.lvl.setOnClickListener {
            onLvlClickListener?.invoke(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isComplete) LVL_COMPLETE else LVL_NOT_COMPLETE
    }
}