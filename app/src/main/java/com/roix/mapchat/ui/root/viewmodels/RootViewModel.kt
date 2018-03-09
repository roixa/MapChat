package com.roix.mapchat.ui.root.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.roix.mapchat.buissness.root.IRootInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.toothpick.root.RootModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.Screen
import com.roix.mapchat.ui.root.models.ToolbarState
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootViewModel : BaseLifecycleViewModel() {

    val toolbarAction = MutableLiveData<NavigationAction>()

    val activeGroup = MutableLiveData<GroupItem>()

    val toolbarState = MutableLiveData<ToolbarState>()

    @Inject
    protected lateinit var rootInteractor: IRootInteractor

    override fun getModule(): Module = RootModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        router.newRootScreen(Screen.GROUP_LIST)
    }

    fun goBack() {
        router.exit()
    }

    fun gotoNewGroupScreen() {
        router.navigateTo(Screen.NEW_GROUP)
    }

    fun gotoChatScreen(groupItem: GroupItem, isFromReplasedScreen: Boolean) {
        activeGroup.value = groupItem
        if(isFromReplasedScreen){
            router.replaceScreen(Screen.GROUP)
        }else{
            router.navigateTo(Screen.GROUP)
        }
    }

    fun gotoInviteScreen(group: GroupItem) {
        router.navigateTo(Screen.INVITATION)
        activeGroup.value = group
    }

    fun gotoShareScreen() {
        router.navigateTo(Screen.SHARE)
    }

    fun onClickedGroupItem(group: GroupItem) {
        if (group.status == GroupItem.Status.NOT_MEMBER) {
            gotoInviteScreen(group)
        } else if (group.status != GroupItem.Status.INFO) {
            gotoChatScreen(group,false)
        }
    }

    fun onReceiveDeepLink(qroupUuid: Long) {
        rootInteractor.proceedReceiveDeepLink(qroupUuid).sub { pair ->
            Log.d("data_boux", "proceedReceiveDeepLink sub isdetermpersoninfo" + pair.first)

            if (pair.first) {
                gotoChatScreen(pair.second,false)
            } else {
                gotoInviteScreen(pair.second)
            }
        }
    }

    fun onOpenFromGroupNotification(group: GroupItem){
        gotoChatScreen(group,false)
    }
}
