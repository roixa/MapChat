package com.roix.mapchat.ui.new_group.viewmodels

import android.databinding.ObservableField
import com.roix.mapchat.buissness.new_group.INewGroupInteractor
import com.roix.mapchat.toothpick.new_group.NewGroupModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module
import java.util.*

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class NewGroupViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: INewGroupInteractor

    val groupName = ObservableField<String>()

    val groupDescription = ObservableField<String>()

    override fun getModule(): Module = NewGroupModule()
}
