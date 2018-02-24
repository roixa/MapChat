package com.roix.mapchat.buissness.share

import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ShareInteractor : IShareInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

}
