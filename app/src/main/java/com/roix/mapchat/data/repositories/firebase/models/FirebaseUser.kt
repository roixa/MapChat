package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.User

/**
 * Created by roix on 22.01.2018.
 */
data class FirebaseUser (val uid:Long?,val name:String?,val isOwner:Boolean?,val iconPos:Int?):Parseble<User> {
    override fun isValid(): Boolean = uid!=null&&name!=null&&isOwner!=null&&iconPos!=null
    override fun parse(): User = User(uid!!,name!!,isOwner!!,iconPos!!)
}