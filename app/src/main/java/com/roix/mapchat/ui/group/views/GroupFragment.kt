package com.roix.mapchat.ui.group.views

import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.group.viewmodels.GroupViewModel
import com.roix.mapchat.ui.group.adapters.GroupPagerAdapter


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
        val pagerAdapter = GroupPagerAdapter(childFragmentManager, activity)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}

