package com.roix.mapchat.buissness.new_group

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface INewGroupInteractor{
    fun createGroup(groupName : String,groupDescr:String, ownerName:String, ownerIconPos:Int
                    , isPrivateGroup:Boolean):
            Single<GroupItem>
    fun getItems(): Single<List<IconItem>>

}
