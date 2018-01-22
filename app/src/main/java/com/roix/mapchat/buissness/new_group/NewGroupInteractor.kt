package com.roix.mapchat.buissness.new_group

import com.roix.mapchat.data.repositories.firebase.IFirebaseRepository
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import com.roix.mapchat.data.repositories.firebase.models.FirebaseUser
import io.reactivex.Completable
import java.util.*

import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class NewGroupInteractor : INewGroupInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

    override fun createGroup(groupName: String, groupDescr: String, ownerName: String): Completable {
        val owner= FirebaseUser(UUID.randomUUID().mostSignificantBits,ownerName,true,-1)
        return firebaseRepository.createGroup(FirebaseGroup(groupName,groupDescr, listOf(owner)))
    }

}
