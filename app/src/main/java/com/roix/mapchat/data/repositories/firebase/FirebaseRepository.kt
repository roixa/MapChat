package com.roix.mapchat.data.repositories.firebase

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.MarkerItem
import com.roix.mapchat.data.models.MessageItem
import com.roix.mapchat.data.models.ShareConfig
import com.roix.mapchat.data.repositories.firebase.models.*
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
    val PREFIX_GROUP = "group_"
    val PREFIX_INVITE = "invite_"

    @Inject constructor() {}

    override fun createGroup(group: FirebaseGroup): Single<GroupItem> = Single.create { e ->
        val owner= group.users!![0]
        val ownerUuid = owner.uid
        database.getReference(PREFIX_GROUP+ownerUuid!!.toString())
                .child("group")
                .setValue(group, { databaseError, databaseReference ->
                    if (databaseError == null) {
                        val ret= group.parse()
                        ret.client=owner.parse()
                        e.onSuccess(ret)
                    } else {
                        e.onError(databaseError.toException())
                    }
                })
    }

    override fun enterToGroup(user: FirebaseUser, groupUuid: Long): Single<GroupItem> = Single.create { e ->
        Log.d("data_boux","FirebaseRepository enterToGroup start "+user.toString())

        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snap: DataSnapshot) {
                val group = snap.child("group").getValue(FirebaseGroup::class.java)
                Log.d("data_boux","FirebaseRepository enterToGroup "+group.toString())

                if (group != null) {
                    group.users!!.add(user)
                    snap.child("group").ref.setValue(group)
                    e.onSuccess(group.parse())
                } else {
                    e.onError(Throwable("invintation failed"))
                }
            }
        }
        database.getReference(PREFIX_GROUP+groupUuid.toString()).addListenerForSingleValueEvent(listener)

    }

    override fun getGroups(lastUUid: Long): Single<List<GroupItem>> = Single.create { e ->
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<GroupItem>()
                for (snap in dataSnapshot.children) {
                    val group = snap.child("group").getValue(FirebaseGroup::class.java)
                    if (group != null && group.isValid() && group.private != true) {
                        //group.ownerUUid = snap.key.toLong()
                        list.add(group.parse())
                    }
                }
                e.onSuccess(list)
            }
        }
        if (lastUUid != -1L) {
            database.reference.orderByKey().limitToFirst(PAGE_ITEMS_SIZE)
                    .startAt(lastUUid.toString())

                    .addListenerForSingleValueEvent(listener)
        } else {
            database.reference.orderByKey().limitToFirst(PAGE_ITEMS_SIZE)
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
                    ret.status = status
                    e.onSuccess(ret)
                } else {
                    e.onComplete()
                }
            }
        }
        database.getReference(PREFIX_GROUP+uid.toString()).addListenerForSingleValueEvent(listener)
    }

    override fun postMessagesInGroupChat(ownerUuid: Long, message: String, author: String, unixTimeStamp: Long
                                         , location: LatLng?): Completable = Completable.create { e ->
        val message = FirebaseMessage(message, author, unixTimeStamp, location?.longitude,
                location?.latitude)
        database.getReference(PREFIX_GROUP+ownerUuid.toString())
                .child("messages").push().setValue(message, { databaseError, databaseReference ->
            if (databaseError == null) {
                e.onComplete()
            } else {
                e.onError(databaseError.toException())
            }
        })
    }

    override fun listenMessagesInGroupChat(ownerUuid: Long): Flowable<List<MessageItem>> = Flowable.create({ e ->
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
        database.getReference(PREFIX_GROUP+ownerUuid.toString()).child("messages").addValueEventListener(listener)
    }, BackpressureStrategy.BUFFER)

    override fun addMarkerToGroup(ownerUuid: Long, marker: MarkerItem): Completable = Completable.create { e ->
        database.getReference(PREFIX_GROUP+ownerUuid.toString())
                .child("markers")
                .child(marker.uuid.toString())
                .setValue(FirebaseMarker(marker), { databaseError, databaseReference ->
                    e.onComplete()
                })
    }

    override fun listenMarkers(ownerUuid: Long): Flowable<List<MarkerItem>> = Flowable.create({ e ->
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<MarkerItem>()
                dataSnapshot.children
                        .mapNotNull { it.getValue(FirebaseMarker::class.java) }
                        .filter { it.isValid() }
                        .mapTo(list) { it.parse() }
                e.onNext(list)
            }
        }
        database.getReference(PREFIX_GROUP+ownerUuid.toString())
                .child("markers")
                .addValueEventListener(listener)

    }, BackpressureStrategy.BUFFER)

    override fun updateUserPosition(groupItem: GroupItem, location: LatLng): Completable = Completable.create {
        Log.d("data_boux","FirebaseRepository updateUserPosition groupItem "+groupItem.toString())

        val client=groupItem.client
        if(client==null){
            it.onError(Throwable("client in group not found"))
            return@create
        }
        val marker=MarkerItem(groupItem.ownerUUid,location,client.name,client.iconPos,client.name,client.uid,System.currentTimeMillis(),true)
        database.getReference(PREFIX_GROUP+groupItem.ownerUUid.toString())
                .child("usersMarkers")
                .child(groupItem.client?.uid.toString())
                .setValue(FirebaseMarker(marker), { databaseError, databaseReference ->
                    it.onComplete()
                })
    }

    override fun listenUsersPositions(groupItem: GroupItem): Flowable<List<MarkerItem>> = Flowable.create({
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<MarkerItem>()
                dataSnapshot.children
                        .mapNotNull { it.getValue(FirebaseMarker::class.java) }
                        .filter { it.isValid() }
                        .mapTo(list) { it.parse() }
                it.onNext(list)
            }
        }
        database.getReference(PREFIX_GROUP+groupItem.ownerUUid.toString())
                .child("usersMarkers")
                .addValueEventListener(listener)
    }, BackpressureStrategy.BUFFER)


    override fun postShareConfig(shareConfig: ShareConfig): Completable = Completable.create{e ->
        Log.d("data_boux","postShareConfig "+shareConfig.toString())
        database.getReference(PREFIX_INVITE+shareConfig.uuid)
                .setValue(FirebaseShareConfig.from(shareConfig), { databaseError, databaseReference ->
                    e.onComplete()
                })
    }

    override fun removeShareConfig(shareConfig: ShareConfig): Completable = Completable.create{ e ->
        Log.d("data_boux","removeShareConfig "+shareConfig.toString())

        database.getReference(PREFIX_INVITE+shareConfig.uuid)
                .removeValue{databaseError, databaseReference ->
                    e.onComplete()
        }
    }

    override fun getShareConfig(configUuid: Long): Single<ShareConfig> = Single.create{e ->

        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val config =dataSnapshot.getValue(FirebaseShareConfig::class.java)
                if(config!=null&&config.isValid()){
                    Log.d("data_boux","getShareConfig "+config.toString())

                    e.onSuccess(config.parse())
                }else{
                    e.onError(Throwable("config_not_found"))
                }
            }
        }
        database.getReference(PREFIX_INVITE+configUuid).addListenerForSingleValueEvent(listener)
    }

}
