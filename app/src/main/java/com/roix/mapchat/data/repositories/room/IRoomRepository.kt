package com.roix.mapchat.data.repositories.room

import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Completable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IRoomRepository {
    fun saveUser(user:RoomUser) :Completable
}