package com.roix.mapchat.data.repositories.firebase

import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.MarkerItem
import com.roix.mapchat.data.models.MessageItem
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IFirebaseRepository {
    fun createGroup(group: FirebaseGroup): Single<GroupItem>
    fun enterToGroup(user: FirebaseUser, groupUuid: Long): Single<GroupItem>
    fun getGroups(lastUUid: Long): Single<List<GroupItem>>
    fun getGroupByOwnerUuid(uid: Long, status: GroupItem.Status): Maybe<GroupItem>
    fun postMessagesInGroupChat(ownerUuid: Long, message: String, author: String, unixTimeStamp: Long
                                , location: LatLng?): Completable

    fun getMessagesInGroupChat(ownerUuid: Long): Flowable<List<MessageItem>>
    fun addMarkerToGroup(ownerUuid: Long, marker: MarkerItem): Completable
    fun getMarkers(ownerUuid: Long):Flowable<List<MarkerItem>>
}