package com.roix.mapchat.buissness.groups

import com.roix.mapchat.data.models.GroupItem
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

    override fun loadItems(page: Long): Single<List<GroupItem>> {
        if (page != -2L) {
            return firebaseRepository.getGroups(page)
        } else {
            return getOwnGroups()
        }
    }

    private fun getOwnGroups(): Single<List<GroupItem>> {
        return databaseRepository.getSavedUsers()
                .flattenAsObservable { t -> t }
                .flatMap { t ->
                    firebaseRepository.getGroupByUserUuid(t.uid).toObservable()
                 }.toList()
    }
}
