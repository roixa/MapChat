package com.roix.mapchat.ui.root.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.roix.mapchat.buissness.root.IRootInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.toothpick.root.RootModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.NavigationState
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootViewModel : BaseLifecycleViewModel() {

    val navigation = MutableLiveData<NavigationState>()

    val toolbarAction = MutableLiveData<NavigationAction>()

    val toolbarTitle = MutableLiveData<String>()

    val activeGroup = MutableLiveData<GroupItem>()

    @Inject
    protected lateinit var rootInteractor: IRootInteractor

    override fun getModule(): Module = RootModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        navigation.value = NavigationState.GROUP_LIST
    }

    fun goBack() {
        when (navigation.value) {
            NavigationState.GROUP_LIST -> navigation.value = NavigationState.FINISHED
            NavigationState.NEW_GROUP -> navigation.value = NavigationState.GROUP_LIST
            NavigationState.INVITATION -> navigation.value = NavigationState.GROUP_LIST
            NavigationState.CHAT -> navigation.value = NavigationState.GROUP_LIST
            NavigationState.MAP -> navigation.value = NavigationState.CHAT
            NavigationState.SHARE -> navigation.value = NavigationState.CHAT
        }
    }

    fun gotoNewGroupScreen() {
        navigation.value = NavigationState.NEW_GROUP
    }

    fun gotoChatScreen(groupItem: GroupItem) {
        activeGroup.value = groupItem
        navigation.value = NavigationState.CHAT
        toolbarTitle.value = groupItem.name
    }

    fun gotoInviteScreen(group: GroupItem){
        navigation.value = NavigationState.INVITATION
        activeGroup.value = group
    }

    fun gotoShareScreen(){
        navigation.value = NavigationState.SHARE
    }

    fun onClickedGroupItem(group: GroupItem) {
        if (group.status == GroupItem.Status.NOT_MEMBER) {
            gotoInviteScreen(group)
        } else if (group.status != GroupItem.Status.INFO) {
            gotoChatScreen(group)
        }
    }

    fun onReceiveDeepLink(qroupUuid:Long){
        rootInteractor.proceedReceiveDeepLink(qroupUuid).sub { pair ->
            Log.d("data_boux","proceedReceiveDeepLink sub isdetermpersoninfo"+pair.first)

            if(pair.first){
                gotoChatScreen(pair.second)
            }else{
                gotoInviteScreen(pair.second)
            }
        }
    }
}
