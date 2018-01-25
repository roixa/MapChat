package com.roix.mapchat.data.repositories.firebase

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IFirebaseRepository {
    fun createGroup(group: FirebaseGroup): Completable
    fun getGroups(lastUUid: Long): Single<List<GroupItem>>
    fun getGroupByUserUuid(uid:Long): Single<GroupItem>
}