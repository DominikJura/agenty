package agh.edu.pl.agenty

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_voice_item.view.device_id as deviceTextView
import kotlinx.android.synthetic.main.view_voice_item.view.voice_value as voiceTextView


class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun setItem(item: VoiceModel) {
        itemView.deviceTextView.text = "${item.deviceId}:"
        itemView.voiceTextView.text = "Volume ${item.value}"
    }

    fun setBackground(@ColorRes color: Int) {
        itemView.setBackgroundColor(getColor(itemView.context, color))
    }
}