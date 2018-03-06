package com.roix.mapchat.ui.share.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.roix.mapchat.buissness.share.IShareInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.ShareConfig
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.share.ShareModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.utils.binding.isValidTextCommon
import io.reactivex.Single
import toothpick.config.Module
import java.util.*
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ShareViewModel : BaseLifecycleViewModel() {

    var currentGroup: GroupItem? = null

    val invite = MutableLiveData<Boolean>()
    val determPerson = MutableLiveData<Boolean>()

    val name = MutableLiveData<String>()
    val nameError = MutableLiveData<Boolean>()

    val choosenIcon = MutableLiveData<IconItem>()
    val icons: ObservableList<IconItem> = ObservableArrayList<IconItem>()

    @Inject
    protected lateinit var interactor: IShareInteractor

    fun shareClickEvent(): Single<String> {
        return if (isValid()) {
            interactor.postShareConfig(ShareConfig(UUID.randomUUID().mostSignificantBits, currentGroup!!.ownerUUid,
                    invite.value == true, determPerson.value == true, name.value, choosenIcon.value?.pos))
        } else {
            Single.create { it.onError(Throwable("is not valid data")) }
        }
    }

    override fun getModule(): Module = ShareModule()

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

    fun receiveCurrentGroup(groupItem: GroupItem) {
        currentGroup = groupItem
    }

    fun isValid(): Boolean {
        var ret = true
        isValidTextCommon(name.value).apply {
            nameError.value = !this
            ret = this || determPerson.value ?: false
        }
        return ret
    }

}
