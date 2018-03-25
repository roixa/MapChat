package com.roix.mapchat.ui.share.views

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.roix.mapchat.R
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.databinding.FragmentShareBinding
import com.roix.mapchat.databinding.IconItemBinding
import com.roix.mapchat.ui.common.adapters.BaseObservableAdapter
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.ToolbarState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
import com.roix.mapchat.ui.share.viewmodels.ShareViewModel
import com.roix.mapchat.utils.ui.ItemClickSupport


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class ShareFragment : BaseDatabindingFragment<ShareViewModel, FragmentShareBinding>() {

    lateinit var rootViewModel: RootViewModel

    override fun getLayoutId(): Int = R.layout.fragment_share

    override fun setupUi() {
        super.setupUi()
        rootViewModel = bindViewModel(RootViewModel::class.java)
        rootViewModel.toolbarState.value = ToolbarState.SHARE
    }

    override fun setupBinding() {
        super.setupBinding()
        binding.rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rv.adapter = BaseObservableAdapter<IconItem, IconItemBinding>(viewModel.icons, R
                .layout.icon_item)
        ItemClickSupport.addTo(binding.rv).setOnItemClickListener { recyclerView, i, view ->
            viewModel.onClickedIcon(i)
            //TODO very strange bug 4
            binding.ivIcon.setImageResource(viewModel.choosenIcon.value!!.resId)
        }

        viewModel.receiveCurrentGroup(rootViewModel.activeGroup.value!!)
    }

}

