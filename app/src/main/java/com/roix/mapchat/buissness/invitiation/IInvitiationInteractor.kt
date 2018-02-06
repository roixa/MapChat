package com.roix.mapchat.buissness.invitiation

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface IInvitiationInteractor{
    fun enterToGroup(groupUuid:Long,uuid:Long,name:String,iconPos:Int) :Single<GroupItem>
    fun getItems(): Single<List<IconItem>>
}
