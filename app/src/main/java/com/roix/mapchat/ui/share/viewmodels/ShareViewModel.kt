package com.roix.mapchat.ui.share.viewmodels

import com.roix.mapchat.buissness.share.IShareInteractor
import com.roix.mapchat.toothpick.share.ShareModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ShareViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: IShareInteractor

    override fun getModule(): Module = ShareModule()
}
