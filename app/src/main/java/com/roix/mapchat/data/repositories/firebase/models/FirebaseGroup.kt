package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User

/**
 * Created by roix on 22.01.2018.
 */
data class FirebaseGroup(var name: String, var descr: String, var users: List<FirebaseUser>?) : Parseble<GroupItem> {

    var ownerUUid: Long = -1

    constructor() : this("", "", null)

    override fun isValid(): Boolean = name != null && descr != null && users != null

    override fun parse(): GroupItem {
        val retList = ArrayList<User>()
        for (user in users!!) {
            if (user.isValid()) {
                retList.add(user.parse())
            }
        }
        return GroupItem(name, descr, retList,ownerUUid)
    }

}