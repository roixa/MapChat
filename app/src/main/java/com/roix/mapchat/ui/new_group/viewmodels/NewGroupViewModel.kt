package com.roix.mapchat.ui.new_group.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.roix.mapchat.buissness.new_group.INewGroupInteractor
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.new_group.NewGroupModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class NewGroupViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: INewGroupInteractor

    val groupName = MutableLiveData<String>()
    val groupDescription = MutableLiveData<String>()
    val ownerName = MutableLiveData<String>()
    val choosenIcon = MutableLiveData<IconItem>()
    val icons: ObservableList<IconItem> = ObservableArrayList<IconItem>()
    val isPrivateGroup = MutableLiveData<Boolean>()

    override fun getModule(): Module = NewGroupModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        interactor.getItems().sub {
            choosenIcon.value = it[0]
            icons.addAll(it)
        }
    }

    fun onClickedIcon(pos: Int) {
        choosenIcon.value = icons[pos]
    }

    fun createGroup() = interactor.createGroup(groupName.value!!, groupDescription.value!!,
            ownerName.value!!, choosenIcon.value!!.pos, isPrivateGroup.value?:false)
}
