package com.roix.mapchat.buissness.new_group

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import com.roix.mapchat.data.repositories.icons.IconsRepository
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.data.repositories.room.RoomRepository
import com.roix.mapchat.data.repositories.room.models.RoomUser
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
    @Inject lateinit var iconRepository: IconsRepository

    override fun createGroup(groupName: String, groupDescr: String, ownerName: String, ownerIconPos: Int, isPrivateGroup: Boolean)
            : Single<GroupItem> {
        val uuid = UUID.randomUUID().mostSignificantBits
        val owner = FirebaseUser(uuid, uuid, ownerName, ownerIconPos)
        return roomRepository.saveUser(RoomUser(owner.parse())).andThen(firebaseRepository
                .createGroup(FirebaseGroup(groupName, groupDescr, mutableListOf(owner), isPrivateGroup)))
    }

    override fun getItems(): Single<List<IconItem>> = iconRepository.getUsersIcons()

}
