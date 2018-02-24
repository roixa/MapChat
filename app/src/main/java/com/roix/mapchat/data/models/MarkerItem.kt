package com.roix.mapchat.data.models

import com.google.android.gms.maps.model.LatLng

/**
 * Created by belyalov on 07.02.2018.
 */
data class MarkerItem(val uuid: Long,
                      val latLng: LatLng,
                      val name: String,
                      val iconPos: Int,
                      val userName: String,
                      val userUuid: Long,
                      val time: Long,
                      val isUser:Boolean)