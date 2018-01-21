package com.roix.mapchat.ui.groups.views

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.databinding.FragmentGroupsBinding
import com.roix.mapchat.databinding.ItemGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseListFragment
import com.roix.mapchat.ui.common.view.ToolbarType
import com.roix.mapchat.ui.groups.viewmodels.GroupsViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class GroupsFragment : BaseListFragment<GroupsViewModel, FragmentGroupsBinding, ItemGroupBinding, GroupItem>() {


    override fun getRecyclerView(): RecyclerView = binding.rv

    override fun getSwipeToRefreshLayout(): SwipeRefreshLayout? = binding.srl

    override fun getLayoutId(): Int = R.layout.fragment_groups

    override fun getItemLayoutId(): Int = R.layout.item_group

}

