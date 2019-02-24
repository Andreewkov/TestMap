package kov.ru.testmap.ui.view

import android.app.Fragment
import android.app.FragmentManager
import android.support.v13.app.FragmentStatePagerAdapter
import kov.ru.testmap.ui.view.fragment.CitiesFragment
import kov.ru.testmap.ui.view.fragment.MapFragment

class TabAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private var mapFragment: MapFragment = MapFragment()
    private var citiesFragment: CitiesFragment = CitiesFragment()

    override fun getItem(p0: Int): Fragment? {
        return when (p0) {
            0 -> mapFragment
            1 -> citiesFragment
            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }

}