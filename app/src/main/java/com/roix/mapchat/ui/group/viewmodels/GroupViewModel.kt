package com.roix.mapchat.ui.group.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.roix.mapchat.buissness.group.IGroupInteractor
import com.roix.mapchat.toothpick.group.GroupModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix on 06.01.2018.
 */
class GroupViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: IGroupInteractor

    override fun getModule(): Module = GroupModule()

    //TODO do retry move it to cicerone
    val isMapSwitch = MutableLiveData<Boolean>()

    fun onTabSelected(pos: Int) {
        when (pos) {
            0 -> isMapSwitch.value = false
            1 -> isMapSwitch.value = true
        }
    }

}
