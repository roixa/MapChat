package com.roix.mapchat.data.repositories.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Completable
import io.reactivex.Single
import java.security.acl.Group
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class FirebaseRepository : IFirebaseRepository {

    val database = FirebaseDatabase.getInstance()

    val PAGE_ITEMS_SIZE = 10

    @Inject constructor() {}

    override fun createGroup(group: FirebaseGroup): Completable = Completable.create{e ->
        val ownerUuid=group.users!![0].uid
        database.getReference("groups")
                .child(ownerUuid!!.toString())
                .child("group")
                .setValue(group,{databaseError, databaseReference ->
            if(databaseError==null){
                e.onComplete()
            }else{
                e.onError(databaseError.toException())
            }
        })
    }

    override fun getGroups(lastUUid: Long): Single<List<GroupItem>> = Single.create{ e ->
        Log.d("boux",lastUUid.toString()+" id")
        val listener=object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list=ArrayList<GroupItem>()
                for(snap in dataSnapshot.children){
                    val group=snap.child("group").getValue(FirebaseGroup::class.java)
                    if(group!=null&&group.isValid()){
                        group.ownerUUid=snap.key.toLong()
                        list.add(group.parse())
                    }
                }
                e.onSuccess(list)
            }

        }
        if(lastUUid!=-1L){
            database.getReference("groups").limitToFirst(10).limitToLast(PAGE_ITEMS_SIZE).addListenerForSingleValueEvent(listener)
        }else{
            database.getReference("groups").limitToLast(PAGE_ITEMS_SIZE).addListenerForSingleValueEvent(listener)
        }

    }


}
