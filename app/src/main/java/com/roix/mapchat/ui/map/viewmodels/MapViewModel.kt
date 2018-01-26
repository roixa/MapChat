package com.roix.mapchat.ui.map.viewmodels

import com.roix.mapchat.buissness.map.IMapInteractor
import com.roix.mapchat.toothpick.map.MapModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module

/**
 * Created by roix on 06.01.2018.
 */
class MapViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: IMapInteractor

    override fun getModule(): Module = MapModule()
}
