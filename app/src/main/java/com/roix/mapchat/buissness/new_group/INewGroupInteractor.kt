package com.roix.mapchat.buissness.new_group

import io.reactivex.Completable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface INewGroupInteractor{
    fun createGroup(groupName : String,groupDescr:String, ownerName:String):Completable
}
