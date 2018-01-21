package com.roix.mapchat.toothpick.common

import android.content.Context
import com.roix.mapchat.application.CommonApplication
import com.roix.mapchat.utils.rx.general.RxSchedulers
import com.roix.mapchat.utils.rx.general.RxSchedulersAbs
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ApplicationModule(val application: CommonApplication) : Module() {
    init {
        bind(Context::class.java).toInstance(application.applicationContext)
        bind(RxSchedulersAbs::class.java).toInstance(RxSchedulers())
    }
}