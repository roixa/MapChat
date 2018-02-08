package com.roix.mapchat.ui.group.views

import android.support.design.widget.TabLayout
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.group.adapters.GroupPagerAdapter
import com.roix.mapchat.ui.group.viewmodels.GroupViewModel
import com.roix.mapchat.ui.root.models.NavigationState
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
        rootViewModel.navigation.sub {
            when(it){
                NavigationState.CHAT->{
                    binding.viewPager.currentItem=0
                }
                NavigationState.MAP ->{
                    binding.viewPager.currentItem=1
                }

            }
        }
        retainInstance = true
        initViewPagerAndTabs()
    }

    private fun initViewPagerAndTabs() {
        val pagerAdapter = GroupPagerAdapter(childFragmentManager, activity)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
                if(tab.position==0){
                    rootViewModel.navigation.value=NavigationState.CHAT
                }else if(tab.position==1){
                    rootViewModel.navigation.value=NavigationState.MAP
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position==0){
                    rootViewModel.navigation.value=NavigationState.CHAT
                }else if(tab.position==1){
                    rootViewModel.navigation.value=NavigationState.MAP
                }
            }

        })
    }

}

