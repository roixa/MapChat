package com.roix.mapchat.toothpick.new_group

import com.roix.mapchat.buissness.new_group.INewGroupInteractor
import com.roix.mapchat.buissness.new_group.NewGroupInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class NewGroupModule : Module() {
    init {
        bind(INewGroupInteractor::class.java).to(NewGroupInteractor::class.java).instancesInScope()
    }
}