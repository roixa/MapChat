package com.roix.mapchat.ui.groups.viewmodels

import com.roix.mapchat.buissness.common.IBaseListInteractor
import com.roix.mapchat.buissness.groups.GroupsInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.toothpick.groups.GroupsModule
import com.roix.mapchat.ui.common.viewmodels.BaseListViewModel
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class GroupsViewModel : BaseListViewModel<GroupItem>() {

    @Inject lateinit var interactor: GroupsInteractor

    override fun getInteractor(): IBaseListInteractor<GroupItem> = interactor

    override fun getModule(): Module = GroupsModule()

    //TODO refactor this bad code
    override fun getNextPage(lastItem: GroupItem): Long {
        if(mNextPage==-2L){
            return -1L
        }
        return lastItem.ownerUUid
    }
}
