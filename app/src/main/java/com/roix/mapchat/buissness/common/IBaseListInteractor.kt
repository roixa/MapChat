package com.roix.mapchat.buissness.common

import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IBaseListInteractor<Item> {
    fun loadItems(page: Long): Single<List<Item>>
}
