package com.roix.mapchat.toothpick.test_architecture

import com.roix.mapchat.buissness.test_architecture.ITestArchitectureInteractor
import com.roix.mapchat.buissness.test_architecture.TestArchitectureInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class TestArchitectureModule : Module() {
    init {
        bind(ITestArchitectureInteractor::class.java).to(TestArchitectureInteractor::class.java).instancesInScope()
    }
}