package com.roix.mapchat.data.repositories.firebase

import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.models.*
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IFirebaseRepository {
    fun createGroup(group: FirebaseGroup): Single<GroupItem>
    fun enterToGroup(user: FirebaseUser, groupUuid: Long): Single<GroupItem>
    fun getGroups(lastUUid: Long): Single<List<GroupItem>>
    fun getGroupBySavedUser(user: User, status: GroupItem.Status): Single<GroupItem>
    fun getGroupByOwnerUuid(uid: Long, status: GroupItem.Status): Single<GroupItem>

    fun postMessagesInGroupChat(ownerUuid: Long, message: String, author: String, unixTimeStamp: Long
                                , location: LatLng?): Completable

    fun listenMessagesInGroupChat(ownerUuid: Long): Flowable<List<MessageItem>>
    fun addMarkerToGroup(ownerUuid: Long, marker: MarkerItem): Completable
    fun listenMarkers(ownerUuid: Long): Flowable<List<MarkerItem>>

    fun updateUserPosition(groupItem: GroupItem, location: LatLng): Completable
    fun listenUsersPositions(groupItem: GroupItem): Flowable<List<MarkerItem>>

    fun postShareConfig(shareConfig: ShareConfig): Completable
    fun removeShareConfig(shareConfig: ShareConfig): Completable
    fun getShareConfig(configUuid: Long): Single<ShareConfig>
}