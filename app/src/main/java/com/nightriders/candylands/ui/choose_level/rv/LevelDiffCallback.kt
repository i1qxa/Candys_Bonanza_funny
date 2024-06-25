package com.nightriders.candylands.ui.choose_level.rv

import androidx.recyclerview.widget.DiffUtil
import com.nightriders.candylands.domain.Level

class LevelDiffCallback:DiffUtil.ItemCallback<Level>() {

    override fun areItemsTheSame(oldItem: Level, newItem: Level): Boolean {
        return oldItem.lvl == newItem.lvl
    }

    override fun areContentsTheSame(oldItem: Level, newItem: Level): Boolean {
        return oldItem == newItem
    }
}