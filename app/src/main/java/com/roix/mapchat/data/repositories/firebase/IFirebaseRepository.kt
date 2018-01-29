package com.roix.mapchat.data.repositories.firebase

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IFirebaseRepository {
    fun createGroup(group: FirebaseGroup): Completable
    fun enterToGroup(user:FirebaseUser,groupUuid:Long):Completable
    fun getGroups(lastUUid: Long): Single<List<GroupItem>>
    fun getGroupByOwnerUuid(uid:Long, status: GroupItem.Status): Maybe<GroupItem>
}