package com.roix.mapchat.data.repositories.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.MessageItem
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseMessage
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.*
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

    override fun createGroup(group: FirebaseGroup): Single<GroupItem> = Single.create { e ->
        val ownerUuid = group.users!![0].uid
        database.getReference("groups")
                .child(ownerUuid!!.toString())
                .child("group")
                .setValue(group, { databaseError, databaseReference ->
                    if (databaseError == null) {
                        e.onSuccess(group.parse())
                    } else {
                        e.onError(databaseError.toException())
                    }
                })
    }

    override fun enterToGroup(user: FirebaseUser, groupUuid: Long): Single<GroupItem> = Single.create { e ->
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snap: DataSnapshot) {
                val group = snap.child("group").getValue(FirebaseGroup::class.java)
                if (group != null) {
                    group.users!!.add(user)
                    snap.child("group").ref.setValue(group)
                    e.onSuccess(group.parse())
                }else{
                    e.onError(Throwable("invintation failed"))
                }
            }
        }
        database.getReference("groups").child(groupUuid.toString()).addListenerForSingleValueEvent(listener)

    }

    override fun getGroups(lastUUid: Long): Single<List<GroupItem>> = Single.create { e ->
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<GroupItem>()
                for (snap in dataSnapshot.children) {
                    val group = snap.child("group").getValue(FirebaseGroup::class.java)
                    if (group != null && group.isValid()) {
                        group.ownerUUid = snap.key.toLong()
                        list.add(group.parse())
                    }
                }
                e.onSuccess(list)
            }
        }
        if (lastUUid != -1L) {
            database.getReference("groups").orderByKey().limitToFirst(PAGE_ITEMS_SIZE)
                    .startAt(lastUUid.toString())
                    .addListenerForSingleValueEvent(listener)
        } else {
            database.getReference("groups").orderByKey().limitToFirst(PAGE_ITEMS_SIZE)
                    .addListenerForSingleValueEvent(listener)
        }

    }

    override fun getGroupByOwnerUuid(uid: Long, status: GroupItem.Status): Maybe<GroupItem> = Maybe.create { e ->
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snap: DataSnapshot) {
                val group = snap.child("group").getValue(FirebaseGroup::class.java)
                if (group != null && group.isValid()) {
                    val ret = group.parse()
                    ret.mStatus = status
                    e.onSuccess(ret)
                } else {
                    e.onComplete()
                }
            }
        }
        database.getReference("groups").child(uid.toString()).addListenerForSingleValueEvent(listener)
    }

    override fun postMessagesInGroupChat(ownerUuid: Long, message: String, author: String, unixTimeStamp: Long
                                         , location: Pair<Double, Double>?): Completable = Completable.create { e ->
        val message = FirebaseMessage(message, author, unixTimeStamp, location)
        database.getReference("groups").child(ownerUuid.toString()).child("messages").push().setValue(message, { databaseError, databaseReference ->
            if (databaseError == null) {
                e.onComplete()
            } else {
                e.onError(databaseError.toException())
            }
        })
    }

    override fun getMessagesInGroupChat(ownerUuid: Long): Flowable<List<MessageItem>> = Flowable.create({ e ->
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<MessageItem>()
                dataSnapshot.children
                        .mapNotNull { it.getValue(FirebaseMessage::class.java) }
                        .filter { it.isValid() }
                        .mapTo(list) { it.parse() }
                list.sortBy { messageItem -> -messageItem.unixTimeStamp }
                e.onNext(list)
            }
        }
            database.getReference("groups").child(ownerUuid.toString()).child("messages").addValueEventListener(listener)

    }, BackpressureStrategy.BUFFER)

}
