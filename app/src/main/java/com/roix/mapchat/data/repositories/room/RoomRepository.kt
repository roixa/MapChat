package com.roix.mapchat.data.repositories.room

import android.content.Context
import com.roix.mapchat.toothpick.common.ApplicationScope
import javax.inject.Inject
import android.arch.persistence.room.Room
import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Completable


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class RoomRepository : IRoomRepository {

    val db: AppDatabase

    @Inject constructor(context: Context) {
        db = Room.databaseBuilder(context,
                AppDatabase::class.java, "database-name").build()
    }

    override fun saveUser(user: RoomUser): Completable = Completable.create{e ->
        db.userDao().insertAll(user)
        e.onComplete()
    }

}
