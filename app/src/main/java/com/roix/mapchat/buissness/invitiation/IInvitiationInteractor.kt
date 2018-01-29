package com.roix.mapchat.buissness.invitiation

import io.reactivex.Completable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface IInvitiationInteractor{
    fun enterToGroup(groupUuid:Long,uuid:Long,name:String,iconPos:Int) :Completable
}
