package com.roix.mapchat.buissness.groups

import android.util.Log
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.room.RoomRepository
import io.reactivex.Single
import javax.inject.Inject

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

    private fun getOwnGroups(): Single<List<GroupItem>> = databaseRepository.getSavedUsers()
            .flattenAsObservable { t -> t }
            .flatMap { t ->
                val status = if (t.uid.equals(t.groupOwnerUuid)) GroupItem.Status.OWNER else GroupItem.Status.MEMBER
                firebaseRepository.getGroupByOwnerUuid(t.groupOwnerUuid, status).toObservable()
            }.filter { t ->
                val has = collisionableGroups.containsKey(t.ownerUUid)
                if (!has) {
                  collisionableGroups.set(t.ownerUUid, t)
                }
                return@filter !has
             }
            .toList()
            .map { t ->
                if (t.isEmpty()) {
                 return@map listOf(GroupItem.createInfoItem())
                } else{
                    return@map t
                }
            }



    private fun getAllGroups(page: Long): Single<List<GroupItem>> =
            firebaseRepository.getGroups(page)
                    .flattenAsObservable { t -> t }
                    .filter { t ->
                        val has = collisionableGroups.containsKey(t.ownerUUid)
                        if (!has) {
                            collisionableGroups.set(t.ownerUUid, t)
                        }
                        return@filter !has
                    }.toList()

}
