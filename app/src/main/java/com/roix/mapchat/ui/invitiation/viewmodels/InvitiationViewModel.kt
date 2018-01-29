package com.roix.mapchat.ui.invitiation.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.roix.mapchat.buissness.invitiation.IInvitiationInteractor
import com.roix.mapchat.toothpick.invitiation.InvitiationModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module
import java.util.*

/**
 * Created by roix on 06.01.2018.
 */
class InvitiationViewModel : BaseLifecycleViewModel() {

    val ownerName = MutableLiveData<String>()

    @Inject
    protected lateinit var interactor: IInvitiationInteractor

    override fun getModule(): Module = InvitiationModule()

    fun enterToGroup(groupUuid:Long ) = interactor.enterToGroup(groupUuid,UUID.randomUUID().mostSignificantBits,ownerName.value!!,-1)

}
