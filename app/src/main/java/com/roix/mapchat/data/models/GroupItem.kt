package com.roix.mapchat.data.models

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class GroupItem(val name: String, val descr: String, val usersCount: Int, val ownerUUid: Long, val ownerName:String) {

    enum class MyStatus{
        NOT_MEMBER,
        MEMBER,
        OWNER
    }
    var status : MyStatus= MyStatus.NOT_MEMBER
}
