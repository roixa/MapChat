package com.roix.mapchat.ui.common.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.roix.mapchat.buissness.common.IBaseListInteractor
import com.roix.mapchat.ui.common.adapters.BaseObservableAdapter
import com.roix.mapchat.ui.common.view.SpaceItemDecoration
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

abstract class BaseListViewModel<Item> : BaseLifecycleViewModel() {

    val items: ObservableList<Item> = ObservableArrayList<Item>()

    protected var mNextPage: Long = getMinPage()

    enum class StateList {
        EMPTY, EMPTY_PROGRESS, EMPTY_ERROR, EMPTY_DATA, DATA, PAGE_PROGRESS, ALL_DATA, PAGE_ERROR, REFRESH
    }

    val stateList = MutableLiveData<StateList>()

    protected abstract fun getInteractor(): IBaseListInteractor<Item>

    open fun <ItemDataBinding : ViewDataBinding> setupRecyclerView(recyclerView: RecyclerView,
                                                                   baseAdapter: BaseObservableAdapter<Item, ItemDataBinding>,
                                                                   swipeToRefreshLayout: SwipeRefreshLayout?) {
        recyclerView.apply {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = baseAdapter
            addItemDecoration(SpaceItemDecoration(context))
            addOnScrollListener(PaginationScrollListener(manager))
            swipeToRefreshLayout?.setOnRefreshListener(SwipeToRefreshListListener())
        }
    }

    override fun onBindFirstView() {
        super.onBindFirstView()
        if (items.isEmpty()) {
            stateList.value = StateList.EMPTY_PROGRESS
        } else {
            stateList.value = StateList.PAGE_PROGRESS
        }
        loadNextItems().subList { l ->
            items.addAll(l)
            if (l.isNotEmpty()) {
                stateList.value = StateList.DATA
            } else {
                if (items.isEmpty()) {
                    stateList.value = StateList.EMPTY_DATA
                } else {
                    stateList.value = StateList.ALL_DATA
                }
            }
        }
    }

    //override if needs
    protected open fun getNextPage(lastItem: Item): Long = mNextPage + 1

    //override if needs
    protected open fun getMinPage(): Long = -2

    //override if needs
    protected open fun getMaxPage(): Long = Long.MAX_VALUE

    protected open fun isLoading(): Boolean = stateList.value!!.equals(StateList.EMPTY_PROGRESS) || stateList.value!!.equals(StateList.PAGE_PROGRESS)

    protected open fun isLastPage(): Boolean = isLastPageVar

    var isLastPageVar = false

    @CallSuper
    open fun refresh() {
        mNextPage = getMinPage()
        stateList.postValue(StateList.REFRESH)
        loadNextItems().subList { list ->
            items.clear()
            items.addAll(list)
            if (items.isEmpty()) {
                stateList.postValue(StateList.EMPTY_DATA)
            } else {
                stateList.postValue(StateList.DATA)
            }
        }
    }

    protected fun loadNextItems(): Single<List<Item>> {
        if (isLastPage()) {
            return Single.create({ e ->
                stateList.value = StateList.ALL_DATA
                e.onSuccess(emptyList())
            })
        } else {
            return getInteractor()
                    .loadItems(mNextPage)
                    .map { t ->
                        val item = t.last()
                        mNextPage = getNextPage(item)
                        return@map t
                    }
        }
    }


    private fun <T> Single<T>.subList(function: (T) -> Unit) {
        subscription.add(
                toObservable()
                        .withDefaultShedulers()
                        .withDefaultLoadingHandle()
                        .subscribe({ t ->
                            function.invoke(t)
                        }, { e ->
                            listErrorHandle(e)
                        })
        )
    }

    private fun listErrorHandle(error: Throwable) {
        errorLiveData.postValue(error)
        if (items.isEmpty()) {
            stateList.value = StateList.EMPTY_ERROR
        } else {
            stateList.value = StateList.PAGE_ERROR
        }
    }

    private inner class PaginationScrollListener(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            if (!isLoading() && !isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    if (stateList.value != StateList.ALL_DATA) {
                        if (items.isEmpty()) {
                            stateList.value = StateList.EMPTY_PROGRESS
                        } else {
                            stateList.value = StateList.PAGE_PROGRESS
                        }
                        loadNextItems().subList { list ->
                            items.addAll(list)
                            if (items.isEmpty()) {
                                stateList.value = StateList.EMPTY_DATA
                            } else {
                                if (list.isEmpty()) {
                                    stateList.value = StateList.ALL_DATA
                                } else {
                                    stateList.value = StateList.DATA
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private inner class SwipeToRefreshListListener : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            refresh()
        }
    }
}
