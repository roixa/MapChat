package com.roix.mapchat.data.models

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class GroupItem(val name: String, val descr: String, val usersCount: Int, val ownerUUid: Long, val ownerName: String) {

    var client: User? = null

    enum class Status {
        NOT_MEMBER,
        MEMBER,
        OWNER,
        INFO
    }

    companion object {
        fun createInfoItem(): GroupItem = GroupItem("hello", "", 0, 0, "")
    }


    var mStatus: Status = Status.NOT_MEMBER
}
