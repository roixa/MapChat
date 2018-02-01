package com.roix.mapchat.ui.test_architecture.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.roix.mapchat.buissness.test_architecture.ITestArchitectureInteractor
import com.roix.mapchat.toothpick.test_architecture.TestArchitectureModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module

/**
 * Created by roix on 06.01.2018.
 */
class TestArchitectureViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: ITestArchitectureInteractor

    override fun getModule(): Module = TestArchitectureModule()

    val count by lazy {
        val ret= MutableLiveData<Int>()
        ret.value =0
        return@lazy ret
    }

}
