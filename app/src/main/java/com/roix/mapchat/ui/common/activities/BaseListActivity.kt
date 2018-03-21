package com.roix.mapchat.ui.common.activities

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.roix.mapchat.ui.common.adapters.BaseObservableAdapter
import com.roix.mapchat.ui.common.view.SpaceItemDecoration
import com.roix.mapchat.ui.common.viewmodels.BaseListViewModel

/**
 * Created by bouxr on 21.03.2018.
 */

abstract class BaseListActivity<ViewModel : BaseListViewModel<Item>, DataBinding : ViewDataBinding, ItemDataBinding : ViewDataBinding, Item>
    : BaseToolbarActivity<ViewModel, DataBinding>() {


    @LayoutRes
    protected abstract fun getItemLayoutId(): Int

    protected abstract fun getRecyclerView(): RecyclerView

    protected abstract fun getSwipeToRefreshLayout(): SwipeRefreshLayout?

    override fun setupUi() {
        super.setupUi()
        setupRecyclerView(getRecyclerView(),
                BaseObservableAdapter<Item, ItemDataBinding>(viewModel.items, getItemLayoutId()),
                getSwipeToRefreshLayout()
        )
    }

    open fun <ItemDataBinding : ViewDataBinding> setupRecyclerView(recyclerView: RecyclerView,
                                                                   baseAdapter: BaseObservableAdapter<Item, ItemDataBinding>,
                                                                   swipeToRefreshLayout: SwipeRefreshLayout?) {
        recyclerView.apply {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = baseAdapter
            addItemDecoration(SpaceItemDecoration(context))
            swipeToRefreshLayout?.setOnRefreshListener(SwipeToRefreshListListener())
        }
    }

    private inner class SwipeToRefreshListListener : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            viewModel.refresh()
        }
    }


}
