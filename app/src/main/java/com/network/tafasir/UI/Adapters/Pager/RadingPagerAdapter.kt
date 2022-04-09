package com.network.tafasir.UI.Adapters.Pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.network.tafasir.UI.Fragments.Quran
import com.network.tafasir.UI.Fragments.ReadSowraF
import com.network.tafasir.UI.Fragments.SowarIndexF


class RadingPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                SowarIndexF()
            }
            1->{
                ReadSowraF()
            }
            else->{
                Fragment()
            }
        }
    }
}