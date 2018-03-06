package com.roix.mapchat.ui.invitiation.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.roix.mapchat.buissness.invitiation.IInvitiationInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.invitiation.InvitiationModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.utils.binding.isValidTextCommon
import io.reactivex.Single
import toothpick.config.Module
import java.util.*
import javax.inject.Inject

/**
 * Created by roix on 06.01.2018.
 */
class InvitiationViewModel : BaseLifecycleViewModel() {

    val ownerName = MutableLiveData<String>()
    val ownerNameError = MutableLiveData<Boolean>()

    val choosenIcon = MutableLiveData<IconItem>()
    val icons: ObservableList<IconItem> = ObservableArrayList<IconItem>()

    @Inject
    protected lateinit var interactor: IInvitiationInteractor

    override fun getModule(): Module = InvitiationModule()


    override fun onBindFirstView() {
        super.onBindFirstView()
        interactor.getItems().sub {
            choosenIcon.value = it[0]
            icons.addAll(it)
        }
    }

    fun enterToGroup(groupUuid:Long ):Single<GroupItem>  {
        if(isValid()){
            return interactor.enterToGroup(groupUuid,UUID.randomUUID().mostSignificantBits,ownerName.value!!,choosenIcon.value!!.pos)
        }else{
            return Single.create{it.onError(Throwable("not valid data"))}
        }
    }

    fun onClickedIcon(pos: Int) {
        choosenIcon.value = icons[pos]
    }

    fun isValid(): Boolean {
        var ret = true
        isValidTextCommon(ownerName.value).apply {
            ownerNameError.value = !this
            ret = this
        }
        return ret
    }


}
