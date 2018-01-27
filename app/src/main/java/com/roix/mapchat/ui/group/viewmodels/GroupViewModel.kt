package com.roix.mapchat.ui.group.viewmodels

import com.roix.mapchat.buissness.group.IGroupInteractor
import com.roix.mapchat.toothpick.group.GroupModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module

/**
 * Created by roix on 06.01.2018.
 */
class GroupViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: IGroupInteractor

    override fun getModule(): Module = GroupModule()

}
