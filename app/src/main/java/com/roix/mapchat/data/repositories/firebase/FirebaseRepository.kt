package com.roix.mapchat.data.repositories.firebase

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Completable
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class FirebaseRepository : IFirebaseRepository {

    val database = FirebaseDatabase.getInstance()

    @Inject constructor() {}

    override fun createGroup(group: FirebaseGroup): Completable = Completable.create{e ->
        database.getReference("groups").push().setValue(group,{databaseError, databaseReference ->
            if(databaseError==null){
                e.onComplete()
            }else{
                e.onError(databaseError.toException())
            }
        })
    }

}
