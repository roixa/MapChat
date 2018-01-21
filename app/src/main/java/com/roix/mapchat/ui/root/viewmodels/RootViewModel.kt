package com.roix.mapchat.ui.root.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.roix.mapchat.buissness.root.IRootInteractor
import com.roix.mapchat.toothpick.root.RootModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.ui.groups.views.GroupsFragment
import com.roix.mapchat.ui.new_group.views.NewGroupFragment
import javax.inject.Inject
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootViewModel : BaseLifecycleViewModel() {

    val navigation = MutableLiveData<State>()

    var state = State.GROUP_LIST

    enum class State {
        FINISHED,
        GROUP_LIST,
        NEW_GROUP
    }

    @Inject
    protected lateinit var rootInteractor: IRootInteractor

    override fun getModule(): Module = RootModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        navigation.value = state
    }

    fun goBack() {
        when (state) {
            State.GROUP_LIST -> state = State.FINISHED
            State.NEW_GROUP -> state = State.GROUP_LIST
        }
        navigation.value = state
    }

    fun gotoNewGroupScreen() {
        state = State.NEW_GROUP
        navigation.value = state
    }
}
