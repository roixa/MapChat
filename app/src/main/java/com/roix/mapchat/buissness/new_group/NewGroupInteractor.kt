package com.roix.mapchat.buissness.new_group

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import com.roix.mapchat.data.repositories.room.RoomRepository
import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class NewGroupInteractor : INewGroupInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

    @Inject lateinit var roomRepository: RoomRepository

    override fun createGroup(groupName: String, groupDescr: String, ownerName: String): Single<GroupItem> {
        val uuid = UUID.randomUUID().mostSignificantBits
        val owner = FirebaseUser(uuid, uuid, ownerName,-1)
        return roomRepository.saveUser(RoomUser(owner.parse())).andThen(firebaseRepository.createGroup(FirebaseGroup(groupName, groupDescr, mutableListOf(owner))))
    }

}
