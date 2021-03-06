package com.roix.mapchat.ui.groups.views

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.databinding.FragmentGroupsBinding
import com.roix.mapchat.databinding.ItemGroupBinding
import com.roix.mapchat.ui.common.fragments.BasePaginationListFragment
import com.roix.mapchat.ui.groups.viewmodels.GroupsViewModel
import com.roix.mapchat.ui.root.models.ToolbarState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
import com.roix.mapchat.utils.ui.ItemClickSupport

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class GroupsFragment : BasePaginationListFragment<GroupsViewModel, FragmentGroupsBinding, ItemGroupBinding, GroupItem>() {


    private lateinit var rootViewModel: RootViewModel

    override fun getRecyclerView(): RecyclerView = binding.rv

    override fun getSwipeToRefreshLayout(): SwipeRefreshLayout? = binding.srl

    override fun getLayoutId(): Int = R.layout.fragment_groups

    override fun getItemLayoutId(): Int = R.layout.item_group

    override fun setupUi() {
        super.setupUi()
        retainInstance=true
        rootViewModel = bindViewModel(RootViewModel::class.java)
        rootViewModel.toolbarState.value = ToolbarState.ROOT
    }

    override fun setupBinding() {
        super.setupBinding()
        binding.fab.setOnClickListener { view ->
            rootViewModel.gotoNewGroupScreen()
        }
        viewModel.stateList.sub { stateList ->
            Log.d("boux", "statelist " + stateList?.name)
        }
        ItemClickSupport.addTo(getRecyclerView())
                .setOnItemClickListener{recyclerView, i, view ->
                    val group = viewModel.items[i]
                    rootViewModel.onClickedGroupItem(group)
        }
    }

    override fun handleProgress(isProgress: Boolean) {

    }

}

