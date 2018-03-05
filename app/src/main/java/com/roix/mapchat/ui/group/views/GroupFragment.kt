package com.roix.mapchat.ui.group.views

import android.support.design.widget.TabLayout
import android.util.Log
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.group.adapters.GroupPagerAdapter
import com.roix.mapchat.ui.group.viewmodels.GroupViewModel
import com.roix.mapchat.ui.root.models.ToolbarState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class GroupFragment : BaseDatabindingFragment<GroupViewModel, FragmentGroupBinding>() {

    lateinit var rootViewModel: RootViewModel

    override fun getLayoutId(): Int = R.layout.fragment_group

    override fun setupBinding() {
        super.setupBinding()
        rootViewModel = bindViewModel(RootViewModel::class.java)
        rootViewModel.toolbarState.value = ToolbarState.GROUP
        retainInstance = true
        initViewPagerAndTabs()
    }

    private fun initViewPagerAndTabs() {
        val pagerAdapter = GroupPagerAdapter(childFragmentManager, activity)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewModel.onTabSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.onTabSelected(tab.position)
            }
        })
        viewModel.isMapSwitch.sub {
            var page = 0
            if (it == true) page = 1
            binding.viewPager.currentItem = page
        }

    }


    fun setPage(page: Int) {
        viewModel.isMapSwitch.value = page == 1
    }

    override fun goBack(): Boolean {
        Log.d("boux", "goback in fragment")
        if (viewModel.isMapSwitch.value == true) {
            setPage(0)
            return true
        }
        return false
    }
}

