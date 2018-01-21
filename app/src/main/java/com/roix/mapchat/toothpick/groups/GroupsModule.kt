package com.roix.mapchat.toothpick.groups

import com.roix.mapchat.buissness.groups.IGroupsInteractor
import com.roix.mapchat.buissness.groups.GroupsInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class GroupsModule : Module() {
    init {
        bind(IGroupsInteractor::class.java).to(GroupsInteractor::class.java).instancesInScope()
    }
}