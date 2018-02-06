package com.roix.mapchat.buissness.map

import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.icons.IconsRepository
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class MapInteractor : IMapInteractor {

    @Inject constructor()

    @Inject lateinit var iconsRepository: IconsRepository
    @Inject lateinit var firebaseRepository: FirebaseRepository


    override fun getItems(): Single<List<IconItem>> = iconsRepository.getMarkersIcons()

}