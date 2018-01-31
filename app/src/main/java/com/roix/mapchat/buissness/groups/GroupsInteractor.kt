package com.roix.mapchat.buissness.groups

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.room.RoomRepository
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class GroupsInteractor : IGroupsInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

    @Inject lateinit var databaseRepository: RoomRepository

    val collisionableGroups: HashMap<Long, GroupItem> = hashMapOf()

    override fun loadItems(page: Long): Single<List<GroupItem>> {
        if (page == -2L) {
            return getOwnGroups()
        } else {
            return getAllGroups(page)
        }
    }

    //TODO add comments
    private fun getOwnGroups(): Single<List<GroupItem>> {
        val users: ArrayList<User> = ArrayList()
        return databaseRepository.getSavedUsers()
                .flattenAsObservable { it }
                .flatMap {
                    users.add(it)
                    val status = if (it.uid.equals(it.groupOwnerUuid)) GroupItem.Status.OWNER else GroupItem.Status.MEMBER
                    firebaseRepository.getGroupByOwnerUuid(it.groupOwnerUuid, status).toObservable()
                }
                .map {
                    it.client = users.removeAt(0)
                    return@map it
                }
                .filter {
                    val has = collisionableGroups.containsKey(it.ownerUUid)
                    if (!has) {
                        collisionableGroups.set(it.ownerUUid, it)
                    }
                    return@filter !has
                }
                .toList()
                .map {
                    if (it.isEmpty()) {
                        return@map listOf(GroupItem.createInfoItem())
                    } else {
                        return@map it
                    }
                }
    }

    //TODO add comments
    private fun getAllGroups(page: Long): Single<List<GroupItem>> {
        val users: ArrayList<User> = ArrayList()
        return databaseRepository.getSavedUsers()
                .flatMap { t ->
                    users.addAll(t)
                    firebaseRepository.getGroups(page)
                }
                .flattenAsObservable { it }
                .filter {
                    val has = collisionableGroups.containsKey(it.ownerUUid)
                    if (!has) {
                        collisionableGroups.set(it.ownerUUid, it)
                    }
                    return@filter !has
                }
                .toList()
    }

}
