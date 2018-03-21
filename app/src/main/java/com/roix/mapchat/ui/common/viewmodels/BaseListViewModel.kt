package com.roix.mapchat.ui.common.viewmodels

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.annotation.CallSuper
import com.roix.mapchat.buissness.common.IBaseListInteractor
import io.reactivex.Single

/**
 * Created by bouxr on 21.03.2018.
 */
abstract class BaseListViewModel <Item> : BaseLifecycleViewModel() {

    val items: ObservableList<Item> = ObservableArrayList<Item>()


    protected abstract fun getInteractor(): IBaseListInteractor<Item>

    override fun onBindFirstView() {
        super.onBindFirstView()
        loadNextItems().sub { l ->
            items.addAll(l)
        }
    }

    @CallSuper
    open fun refresh() {
        loadNextItems().sub { list ->
            items.clear()
            items.addAll(list)
        }
    }

    protected fun loadNextItems(): Single<List<Item>> {
            return getInteractor()
                    .loadItems(0)
    }


}
