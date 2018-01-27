package com.roix.mapchat.ui.root.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.roix.mapchat.buissness.root.IRootInteractor
import com.roix.mapchat.toothpick.root.RootModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.NavigationState
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootViewModel : BaseLifecycleViewModel() {

    val navigation = MutableLiveData<NavigationState>()

    val toolbarAction = MutableLiveData<NavigationAction>()

    val activeGroupUuid = MutableLiveData<Long>()

    @Inject
    protected lateinit var rootInteractor: IRootInteractor

    override fun getModule(): Module = RootModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        navigation.value = NavigationState.GROUP_LIST
    }

    fun goBack() {
        when (navigation.value) {
            NavigationState.GROUP_LIST -> navigation.value = NavigationState.FINISHED
            NavigationState.NEW_GROUP -> navigation.value = NavigationState.GROUP_LIST
            NavigationState.CHAT -> navigation.value = NavigationState.GROUP_LIST
        }
    }

    fun gotoNewGroupScreen() {
        navigation.value = NavigationState.NEW_GROUP
    }

    fun gotoGroupScreen(uuid: Long) {
        navigation.value = NavigationState.CHAT
        activeGroupUuid.value = uuid
    }
}
