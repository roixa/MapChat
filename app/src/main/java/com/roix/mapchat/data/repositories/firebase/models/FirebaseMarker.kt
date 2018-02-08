package com.roix.mapchat.data.repositories.firebase.models

import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.MarkerItem

/**
 * Created by belyalov on 07.02.2018.
 */
class FirebaseMarker() : Parseble<MarkerItem> {

    var uuid: Long? = null
    var longitude: Double? = null
    var lattitude: Double? = null

    var name: String? = null
    var iconPos: Int? = null
    var userName: String? = null
    var userUuid: Long? = null
    var time: Long? = null

    constructor(marker: MarkerItem) : this() {
        uuid = marker.uuid
        lattitude = marker.latLng.latitude
        longitude = marker.latLng.longitude
        name = marker.name
        iconPos = marker.iconPos
        userName = marker.userName
        userUuid = marker.userUuid
        time = marker.time
    }

    override fun isValid(): Boolean =
            uuid != null && lattitude != null
                    && longitude != null && name != null
                    && iconPos != null && userName != null
                    && userUuid != null && time != null

    override fun parse(): MarkerItem = MarkerItem(uuid!!, LatLng(lattitude!!, longitude!!),
            name!!, iconPos!!,
            userName!!, userUuid!!, time!!)

}