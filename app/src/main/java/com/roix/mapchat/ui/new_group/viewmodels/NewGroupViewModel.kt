package com.roix.mapchat.ui.new_group.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.roix.mapchat.buissness.new_group.INewGroupInteractor
import com.roix.mapchat.toothpick.new_group.NewGroupModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module
import java.util.*

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class NewGroupViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: INewGroupInteractor

    val groupName = MutableLiveData<String>()

    val groupDescription = MutableLiveData<String>()

    val ownerName = MutableLiveData<String>()

    override fun getModule(): Module = NewGroupModule()

    fun createGroup() = interactor.createGroup(groupName.value!!, groupDescription.value!!, ownerName.value!!)
}
