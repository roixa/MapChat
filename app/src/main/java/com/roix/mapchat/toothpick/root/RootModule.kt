package com.roix.mapchat.toothpick.root

import com.roix.mapchat.buissness.root.IRootInteractor
import com.roix.mapchat.buissness.root.RootInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootModule : Module() {
    init {
        bind(IRootInteractor::class.java).to(RootInteractor::class.java).instancesInScope()
    }
}