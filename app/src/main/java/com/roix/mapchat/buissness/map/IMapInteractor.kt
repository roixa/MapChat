package com.roix.mapchat.buissness.map

import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.models.MarkerItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface IMapInteractor {
    fun getIcons(): Single<List<IconItem>>
    fun addMarker(groupOwnerUuid: Long, text: String,
                  latLng: LatLng, iconPos: Int,
                  userName: String, userUuid: Long)
            : Completable

    fun getMarkers(groupOwnerUuid: Long): Flowable<List<MarkerItem>>
}
