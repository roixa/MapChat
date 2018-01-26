package com.roix.mapchat.ui.common.adapters

import android.app.Fragment
import android.app.FragmentManager
import android.support.v13.app.FragmentPagerAdapter
import android.view.View


/**
 * Created by belyalov on 26.01.2018.
 */
class PagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()

    fun addFragment(fragment:Fragment,title:String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }


    override fun getCount():Int = fragmentList.size

    override fun getPageTitle(position:Int):String = fragmentTitleList.get(position)


    override fun getItem(position: Int): Fragment = fragmentList[position]

}