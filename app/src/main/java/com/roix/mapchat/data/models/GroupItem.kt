package com.roix.mapchat.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class GroupItem(val name: String, val descr: String, val users: List<User>, val ownerUUid: Long, val ownerName: String) : Serializable, Parcelable {

    var client: User? = null

    enum class Status {
        NOT_MEMBER,
        MEMBER,
        OWNER,
        INFO
    }

    var status: Status = Status.NOT_MEMBER

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(User),
            parcel.readLong(),
            parcel.readString()) {
        client = parcel.readParcelable(User::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(descr)
        parcel.writeTypedList(users)
        parcel.writeLong(ownerUUid)
        parcel.writeString(ownerName)
        parcel.writeParcelable(client, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupItem> {
        fun createInfoItem(): GroupItem = GroupItem("hello", "", emptyList(), 0, "")

        override fun createFromParcel(parcel: Parcel): GroupItem {
            return GroupItem(parcel)
        }

        override fun newArray(size: Int): Array<GroupItem?> {
            return arrayOfNulls(size)
        }
    }
}
