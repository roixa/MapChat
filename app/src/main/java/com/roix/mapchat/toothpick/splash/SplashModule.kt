package com.roix.mapchat.toothpick.splash

import com.roix.mapchat.buissness.splash.ISplashInteractor
import com.roix.mapchat.buissness.splash.SplashInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class SplashModule : Module() {
    init {
        bind(ISplashInteractor::class.java).to(SplashInteractor::class.java).instancesInScope()
    }
}