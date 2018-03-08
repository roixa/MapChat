package com.roix.mapchat.data.models

import java.io.Serializable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class GroupItem(val name: String, val descr: String, val users: List<User>, val ownerUUid: Long, val ownerName: String) : Serializable {

    var client: User? = null

    enum class Status {
        NOT_MEMBER,
        MEMBER,
        OWNER,
        INFO
    }

    fun isEmpty() = ownerUUid==-1L
    companion object {
        fun createInfoItem(): GroupItem = GroupItem("hello", "", emptyList(), 0, "")
        fun createEmptyItem(): GroupItem = GroupItem("", "", emptyList(), -1, "")
    }

    var status: Status = Status.NOT_MEMBER

}
