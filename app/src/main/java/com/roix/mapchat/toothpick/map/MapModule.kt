package com.roix.mapchat.toothpick.map

import com.roix.mapchat.buissness.map.IMapInteractor
import com.roix.mapchat.buissness.map.MapInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class MapModule : Module() {
    init {
        bind(IMapInteractor::class.java).to(MapInteractor::class.java).instancesInScope()
    }
}