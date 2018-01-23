package com.roix.mapchat.data.repositories.room

import android.arch.persistence.room.*
import android.arch.persistence.room.Update
import com.roix.mapchat.data.repositories.room.models.RoomUser


/**
 * Created by roix on 23.01.2018.
 */
@Dao
interface UserDao{

    @Query("SELECT * FROM users")
    fun getAll(): List<RoomUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: RoomUser)

    @Update
    fun updateUsers(vararg users: RoomUser)

}