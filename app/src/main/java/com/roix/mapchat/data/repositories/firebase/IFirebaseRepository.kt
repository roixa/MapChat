package com.roix.mapchat.data.repositories.firebase

import com.roix.mapchat.data.repositories.firebase.models.FirebaseGroup
import io.reactivex.Completable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IFirebaseRepository {
    fun createGroup(group: FirebaseGroup): Completable
}