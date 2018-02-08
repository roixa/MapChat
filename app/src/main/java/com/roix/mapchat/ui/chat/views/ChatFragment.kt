package com.roix.mapchat.ui.chat.views

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.roix.mapchat.R
import com.roix.mapchat.data.models.MessageItem
import com.roix.mapchat.databinding.FragmentChatBinding
import com.roix.mapchat.databinding.ItemMessageBinding
import com.roix.mapchat.ui.chat.viewmodels.ChatViewModel
import com.roix.mapchat.ui.common.fragments.BaseListFragment
import com.roix.mapchat.ui.map.viewmodels.MapViewModel
import com.roix.mapchat.ui.root.models.NavigationState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
import com.roix.mapchat.utils.ui.ItemClickSupport

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class ChatFragment : BaseListFragment<ChatViewModel, FragmentChatBinding, ItemMessageBinding, MessageItem>() {

    lateinit var rootViewModel: RootViewModel
    lateinit var mapViewModel: MapViewModel

    override fun setupBinding() {
        super.setupBinding()
        rootViewModel = bindViewModel(RootViewModel::class.java)
        mapViewModel = bindViewModel(MapViewModel::class.java)
        rootViewModel.activeGroup.sub { groupItem ->
            if (groupItem?.client != null) {
                viewModel.onReceiveData(groupItem.ownerUUid, groupItem.client!!.name)
            }
        }
        binding.ivSend.setOnClickListener {
            viewModel.onPostMessageClicked()
        }
        ItemClickSupport.addTo(binding.rv).setOnChildClickListener(R.id.iv_to_map_arrow, { recyclerView, i, view ->
            mapViewModel.focusLocation.value = viewModel.items[i].location
            rootViewModel.navigation.value = NavigationState.MAP
            return@setOnChildClickListener false
        })
    }

    override fun getRecyclerView(): RecyclerView = binding.rv

    override fun getSwipeToRefreshLayout(): SwipeRefreshLayout? = null

    override fun getLayoutId(): Int = R.layout.fragment_chat

    override fun getItemLayoutId(): Int = R.layout.item_message

    override fun handleProgress(isProgress: Boolean) {}


}

