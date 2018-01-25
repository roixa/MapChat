package com.roix.mapchat.data.repositories.room

import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IRoomRepository {
    fun saveUser(user:RoomUser) :Completable

    fun getSavedUsers() :Single<List<User>>

}