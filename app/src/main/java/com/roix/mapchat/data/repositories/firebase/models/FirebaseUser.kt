package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.User

/**
 * Created by roix on 22.01.2018.
 */
data class FirebaseUser (val uid:Long?,val name:String?,val owner:Boolean?,val iconPos:Int?):Parseble<User> {
    constructor():this(null,null,null,null)
    override fun isValid(): Boolean = uid!=null&&name!=null&&owner!=null&&iconPos!=null
    override fun parse(): User = User(uid!!,name!!,owner!!,iconPos!!)
}