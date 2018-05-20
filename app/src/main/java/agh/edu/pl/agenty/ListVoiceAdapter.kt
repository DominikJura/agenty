package agh.edu.pl.agenty

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup

class ListVoiceAdapter : ListAdapter<VoiceModel, ListViewHolder>(ListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
            ListViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_voice_item, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        when(position % 2) {
            0 -> holder.setBackground(R.color.grey)
            else -> holder.setBackground(R.color.grey2)
        }
        holder.setItem(getItem(position))
    }
}