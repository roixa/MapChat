package com.roix.mapchat.buissness.root

import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.location.LocationRepository
import io.reactivex.Completable

import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootInteractor : IRootInteractor {
    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var locationRepository: LocationRepository

    override fun enterToGroup(groupItem: GroupItem): Completable = locationRepository.requestLocationsToGroup(groupItem)


}
