package agh.edu.pl.agenty.list

import agh.edu.pl.agenty.model.VoiceModel
import android.support.v7.util.DiffUtil

class ListDiffCallback : DiffUtil.ItemCallback<VoiceModel>() {

    override fun areItemsTheSame(oldItem: VoiceModel, newItem: VoiceModel): Boolean =
            oldItem.deviceId == newItem.deviceId

    override fun areContentsTheSame(oldItem: VoiceModel, newItem: VoiceModel): Boolean =
            oldItem.deviceId == newItem.deviceId && oldItem.value == newItem.value
}