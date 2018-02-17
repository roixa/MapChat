package com.roix.mapchat.data.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by roix on 23.01.2018.
 */
data class User(val uid: Long, val groupOwnerUuid: Long, val name: String, val iconPos: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(uid)
        parcel.writeLong(groupOwnerUuid)
        parcel.writeString(name)
        parcel.writeInt(iconPos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}