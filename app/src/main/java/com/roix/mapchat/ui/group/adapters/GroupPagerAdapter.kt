package com.roix.mapchat.ui.group.adapters

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.support.v13.app.FragmentPagerAdapter
import android.util.Log
import android.view.View
import com.roix.mapchat.R
import com.roix.mapchat.ui.chat.views.ChatFragment
import com.roix.mapchat.ui.map.views.MapFragment


/**
 * Created by belyalov on 26.01.2018.
 */
class GroupPagerAdapter(fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): String {
        if (position == 0) {
            return context.getString(R.string.title_chat)
        }
        return context.getString(R.string.title_map)

    }


    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return ChatFragment()
        }
        return MapFragment()
    }

}