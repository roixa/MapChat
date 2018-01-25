package com.roix.mapchat.data.repositories.room.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.User

/**
 * Created by roix on 23.01.2018.
 */
@Entity(tableName = "users")
data class RoomUser(@PrimaryKey var uid:Long,var name:String,var isOwner:Boolean,var iconPos:Int)
    :Parseble<User> {
    constructor(user: User):this(user.uid,user.name,user.isOwner,user.iconPos)
    override fun isValid(): Boolean = uid!=null&&name!=null&&isOwner!=null&&iconPos!=null
    override fun parse(): User = User(uid!!,name!!,isOwner!!,iconPos!!)
}