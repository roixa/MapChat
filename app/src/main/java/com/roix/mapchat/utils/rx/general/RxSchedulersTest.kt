package com.roix.mapchat.utils.rx.general

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RxSchedulersTest : RxSchedulersAbs() {

    override val mainThreadScheduler: Scheduler
        get() = Schedulers.trampoline()

    override val ioScheduler: Scheduler
        get() = Schedulers.trampoline()

    override val computationScheduler: Scheduler
        get() = Schedulers.trampoline()
}