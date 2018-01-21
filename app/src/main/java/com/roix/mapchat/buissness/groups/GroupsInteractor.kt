package com.roix.mapchat.buissness.groups

import com.roix.mapchat.data.repositories.firebase.IFirebaseRepository
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import javax.inject.Inject
import com.roix.mapchat.data.models.GroupItem
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class GroupsInteractor : IGroupsInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

    override fun loadItems(page: Int): Single<List<GroupItem>> = Single.never()


}
