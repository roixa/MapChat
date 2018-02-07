package com.roix.mapchat.data.repositories.firebase

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.Marker
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
                                , location: Pair<Double, Double>?): Completable

    fun getMessagesInGroupChat(ownerUuid: Long): Flowable<List<MessageItem>>
    fun addMarkerIToGroup(ownerUuid: Long, marker: Marker): Completable
    fun getMarkers(ownerUuid: Long):Flowable<List<Marker>>
}