package agh.edu.pl.agenty

import agh.edu.pl.agenty.list.ListFragment
import agh.edu.pl.agenty.map.MapFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class VoicePageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        VoiceFragments.LIST.ordinal -> ListFragment()
        VoiceFragments.MAP.ordinal -> MapFragment()
        else -> throw RuntimeException("Illegal summary page position: $position")
    }

    override fun getCount(): Int = VoiceFragments.values().size
}

enum class VoiceFragments(val drawableRes: Int) {
    LIST(R.drawable.ic_list_black_36dp),
    MAP(R.drawable.ic_place_black_36dp)
}