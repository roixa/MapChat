package com.roix.mapchat.buissness.groups

import android.util.Log
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

    //TODO add comments (its server login on the client)
    private fun getOwnGroups(): Single<List<GroupItem>> {
        val users: ArrayList<User> = ArrayList()
        return databaseRepository.getSavedUsers()
                .flattenAsObservable { it }
                .flatMap {
                    users.add(it)
                    Log.d("data_boux", "getOwnGroups saved user" + it.toString())
                    val status = if (it.uid.equals(it.groupOwnerUuid)) GroupItem.Status.OWNER else GroupItem.Status.MEMBER
                    firebaseRepository.getGroupByOwnerUuid(it.uid, status).toObservable()
                }
                .map {
                    if (!it.isEmpty()) {
                        it.client = users.find { user -> user.uid == it.ownerUUid }
                    }
                    Log.d("data_boux", "getOwnGroups group " + it.toString())
                    return@map it
                }
                .filter {
                    val has = collisionableGroups.containsKey(it.ownerUUid)
                    if (!has) {
                        collisionableGroups.set(it.ownerUUid, it)
                    }
                    return@filter !has && !it.isEmpty()
                }
                .toList()
                .map {
                    Log.d("data_boux", "groupInteractor result" + it.toString())

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
                .map {
                    it.client = users
                            .zip(it.users)
                            .filter { pair ->
                                pair.first.uid == pair.second.uid
                            }
                            .last().first
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
    }

}
