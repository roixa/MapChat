package com.roix.mapchat.ui.new_group.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.roix.mapchat.buissness.new_group.INewGroupInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.new_group.NewGroupModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.utils.binding.isValidTextCommon
import io.reactivex.Single
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
    val groupNameError = MutableLiveData<Boolean>()

    val groupDescription = MutableLiveData<String>()
    val groupDescriptionError = MutableLiveData<Boolean>()

    val ownerName = MutableLiveData<String>()
    val ownerNameError = MutableLiveData<Boolean>()

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

    fun createGroup():Single<GroupItem> {
        if(isValid ()){
            return interactor.createGroup(groupName.value!!, groupDescription.value!!,
                    ownerName.value!!, choosenIcon.value!!.pos, isPrivateGroup.value ?: false)
        }else{
            return Single.create{it.onError(Throwable("not valid data"))}
        }
    }

    fun isValid(): Boolean {
        var ret = true
        isValidTextCommon(groupName.value).apply {
            groupNameError.value = !this
            ret = this
        }
        isValidTextCommon(groupDescription.value).apply {
            groupDescriptionError.value = !this
            ret = this
        }
        isValidTextCommon(ownerName.value).apply {
            ownerNameError.value = !this
            ret = this
        }
        return ret
    }
}
