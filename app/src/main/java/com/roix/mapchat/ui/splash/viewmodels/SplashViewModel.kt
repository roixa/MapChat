package com.roix.mapchat.ui.splash.viewmodels

import com.roix.mapchat.buissness.splash.ISplashInteractor
import com.roix.mapchat.toothpick.splash.SplashModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import io.reactivex.Single
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class SplashViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: ISplashInteractor

    override fun getModule(): Module = SplashModule()

    fun prepareApplication(): Single<Boolean> = Single.create{
        it.onSuccess(true)
    }
}
