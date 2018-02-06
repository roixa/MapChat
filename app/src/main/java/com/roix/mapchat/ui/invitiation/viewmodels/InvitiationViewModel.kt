package com.roix.mapchat.ui.invitiation.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.roix.mapchat.buissness.invitiation.IInvitiationInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.invitiation.InvitiationModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import io.reactivex.Single
import javax.inject.Inject
import toothpick.config.Module
import java.util.*

/**
 * Created by roix on 06.01.2018.
 */
class InvitiationViewModel : BaseLifecycleViewModel() {

    val ownerName = MutableLiveData<String>()
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

    fun enterToGroup(groupUuid:Long ):Single<GroupItem> = interactor.enterToGroup(groupUuid,UUID
            .randomUUID().mostSignificantBits,ownerName.value!!,choosenIcon.value!!.pos)

    fun onClickedIconInCreateDialog(pos: Int) {
        choosenIcon.value = icons[pos]
    }

}
