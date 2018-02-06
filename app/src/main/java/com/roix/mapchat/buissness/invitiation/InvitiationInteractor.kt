package com.roix.mapchat.buissness.invitiation

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import com.roix.mapchat.data.repositories.icons.IconsRepository
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.data.repositories.room.RoomRepository
import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class InvitiationInteractor : IInvitiationInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var roomRepository: RoomRepository
    @Inject lateinit var iconRepository: IconsRepository

    override fun enterToGroup(groupUuid: Long, uuid: Long, name: String, iconPos: Int): Single<GroupItem> {
        val user = User(uuid, groupUuid, name, iconPos)
        return roomRepository.saveUser(RoomUser(user)).andThen(firebaseRepository.enterToGroup(FirebaseUser(uuid,groupUuid, name, iconPos), groupUuid))
    }

    override fun getItems(): Single<List<IconItem>> = iconRepository.getUsersIcons()

}
