package com.roix.mapchat.ui.group.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.group.viewmodels.GroupViewModel
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.roix.mapchat.ui.chat.views.ChatFragment
import com.roix.mapchat.ui.common.adapters.PagerAdapter
import com.roix.mapchat.ui.map.views.MapFragment


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class GroupFragment : BaseDatabindingFragment<GroupViewModel, FragmentGroupBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_group

    override fun setupBinding() {
        super.setupBinding()
        initViewPagerAndTabs()
    }

    private fun initViewPagerAndTabs() {
        val pagerAdapter = PagerAdapter(activity.fragmentManager)
        pagerAdapter.addFragment(ChatFragment(), getString(R.string.title_chat))
        pagerAdapter.addFragment(MapFragment(), getString(R.string.title_map))
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}

