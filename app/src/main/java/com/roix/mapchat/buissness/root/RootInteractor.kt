package com.roix.mapchat.buissness.root

import android.util.Log
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.User
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import com.roix.mapchat.data.repositories.room.RoomRepository
import com.roix.mapchat.data.repositories.room.models.RoomUser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootInteractor : IRootInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var roomRepository: RoomRepository

    //return boolean is isDetermPerson
    override fun proceedReceiveDeepLink(groupUuid: Long): Single<Pair<Boolean, GroupItem>> = firebaseRepository.getShareConfig(groupUuid)
            .flatMap { t ->
                Log.d("data_boux","proceedReceiveDeepLink getShareConfig "+t.toString())

                firebaseRepository.getGroupByOwnerUuid(t.groupUuid, GroupItem.Status.NOT_MEMBER)
                        .flatMap { group ->
                            Log.d("data_boux","proceedReceiveDeepLink getGroupByOwnerUuid "+group.toString())

                            if (t.isDetermPerson) {
                                val uuid = UUID.randomUUID().mostSignificantBits
                                val user = User(uuid, t.groupUuid, t.username!!, t.iconPos!!)
                                return@flatMap roomRepository.saveUser(RoomUser(user)).subscribeOn(Schedulers.io())
                                        .andThen(firebaseRepository.enterToGroup(FirebaseUser(uuid, t.groupUuid, t.username, t.iconPos), t.groupUuid))

                            } else {
                                return@flatMap Single.create<GroupItem> { it.onSuccess(group) }
                            }
                        }
                        .map { Pair(t, it) }
            }
            .flatMap { t ->
                Log.d("data_boux","proceedReceiveDeepLink flatMap "+t.toString())

                if (t.first.isInvite) {
                    return@flatMap firebaseRepository.removeShareConfig(t.first).toSingle { Pair(t.first.isDetermPerson, t.second) }
                } else {
                    return@flatMap Single.create<Pair<Boolean, GroupItem>> {
                        it.onSuccess(Pair(t.first.isDetermPerson, t.second))
                    }
                }
            }

}
