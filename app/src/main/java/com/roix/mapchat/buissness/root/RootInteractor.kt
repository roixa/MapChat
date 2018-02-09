package com.roix.mapchat.buissness.root

import com.roix.mapchat.data.repositories.firebase.IFirebaseRepository
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.location.LocationRepository

import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class RootInteractor : IRootInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var locationRepository: LocationRepository

}
