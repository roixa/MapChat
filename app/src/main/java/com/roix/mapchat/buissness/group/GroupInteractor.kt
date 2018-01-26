package com.roix.mapchat.buissness.group

import com.roix.mapchat.data.repositories.firebase.IFirebaseRepository
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository

import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class GroupInteractor : IGroupInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

}
