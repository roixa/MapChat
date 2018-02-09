package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User

/**
 * Created by roix on 22.01.2018.
 */
data class FirebaseGroup(var name: String?, var descr: String?, var users:
    MutableList<FirebaseUser>?, var private: Boolean?) :
        Parseble<GroupItem> {


    constructor() : this("", "", null, false)

    override fun isValid(): Boolean = name != null && descr != null && users != null

    override fun parse(): GroupItem {
        var ownerUUid: Long = -1
        var ownerName: String = ""
        val retList = ArrayList<User>()
        for (user in users!!) {
            if (user.isValid()) {
                retList.add(user.parse())
            }
            if (user.groupOwnerUuid == user.uid) {
                ownerUUid = user.uid!!
                ownerName = user.name!!
            }
        }
        return GroupItem(name!!, descr!!, retList, ownerUUid, ownerName)
    }

}