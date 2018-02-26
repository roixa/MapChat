package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.User

/**
 * Created by roix on 22.01.2018.
 */
data class FirebaseUser(var uid: Long?, var groupOwnerUuid: Long?, var name: String?, var iconPos: Int?) : Parseble<User> {
    constructor() : this(null, null, null, null)

    override fun isValid(): Boolean = uid != null && name != null && iconPos != null && groupOwnerUuid != null
    override fun parse(): User = User(uid!!, groupOwnerUuid!!, name!!, iconPos!!)
}