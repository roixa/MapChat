package com.roix.mapchat.data.repositories.room

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.room.models.RoomUser
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


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

    override fun saveUser(user: RoomUser): Completable = Completable.create{
        Log.d("data_boux","RoomRepository saveUser "+user.toString()+ it.toString())

        db.userDao().insertAll(user)
        it.onComplete()
    }

    override fun getSavedUsers(): Single<List<User>> = Single.create{e ->

        val users = db.userDao().getAll()
        Log.d("data_boux","RoomRepository getSavedUsers "+users.toString())
        val ret= ArrayList<User>()
        for(user in users){
            if(user.isValid()){
                ret.add(user.parse())
            }
        }
        Log.d("boux","getSavedUsers size "+ret.size)
        e.onSuccess(ret)
    }


}
