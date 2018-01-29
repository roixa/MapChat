package com.roix.mapchat.buissness.invitiation

import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import com.roix.mapchat.data.repositories.room.RoomRepository
import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class InvitiationInteractor : IInvitiationInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var roomRepository: RoomRepository

    override fun enterToGroup(groupUuid: Long, uuid: Long, name: String, iconPos: Int): Completable {
        val user = User(uuid, groupUuid, name, iconPos)
        return roomRepository.saveUser(RoomUser(user)).mergeWith(firebaseRepository.enterToGroup
        (FirebaseUser(uuid,groupUuid, name, iconPos), groupUuid))
    }

}
