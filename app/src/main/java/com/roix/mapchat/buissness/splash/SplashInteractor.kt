package com.roix.mapchat.buissness.splash

import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class SplashInteractor : ISplashInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

}
