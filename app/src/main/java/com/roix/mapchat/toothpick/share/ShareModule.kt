package com.roix.mapchat.toothpick.share

import com.roix.mapchat.buissness.share.IShareInteractor
import com.roix.mapchat.buissness.share.ShareInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ShareModule : Module() {
    init {
        bind(IShareInteractor::class.java).to(ShareInteractor::class.java).instancesInScope()
    }
}