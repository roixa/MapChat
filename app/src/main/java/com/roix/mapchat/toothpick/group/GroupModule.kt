package com.roix.mapchat.toothpick.group

import com.roix.mapchat.buissness.group.IGroupInteractor
import com.roix.mapchat.buissness.group.GroupInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class GroupModule : Module() {
    init {
        bind(IGroupInteractor::class.java).to(GroupInteractor::class.java).instancesInScope()
    }
}