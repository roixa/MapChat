package com.roix.mapchat.data.repositories.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.roix.mapchat.data.repositories.room.models.RoomUser

/**
 * Created by roix on 23.01.2018.
 */
@Database(entities = arrayOf(RoomUser::class),version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}