package com.roix.mapchat.toothpick.invitiation

import com.roix.mapchat.buissness.invitiation.IInvitiationInteractor
import com.roix.mapchat.buissness.invitiation.InvitiationInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class InvitiationModule : Module() {
    init {
        bind(IInvitiationInteractor::class.java).to(InvitiationInteractor::class.java).instancesInScope()
    }
}