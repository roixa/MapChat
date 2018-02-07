package com.roix.mapchat.buissness.map

import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.models.Marker
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.icons.IconsRepository
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class MapInteractor : IMapInteractor {

    @Inject constructor()

    @Inject lateinit var iconsRepository: IconsRepository
    @Inject lateinit var firebaseRepository: FirebaseRepository


    override fun getIcons(): Single<List<IconItem>> = iconsRepository.getMarkersIcons()

    override fun addMarker(groupOwnerUuid: Long, text: String, latLng: LatLng, iconPos: Int, userName: String, userUuid: Long): Completable {
        return firebaseRepository.addMarkerIToGroup(groupOwnerUuid,
                Marker(UUID.randomUUID().mostSignificantBits,
                        latLng, text, iconPos, userName,
                        userUuid, System.currentTimeMillis()
                )
        )
    }

    override fun getMarkers(groupOwnerUuid: Long): Flowable<List<Marker>> = firebaseRepository.getMarkers(groupOwnerUuid)
}