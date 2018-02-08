package com.roix.mapchat.data.models

import com.google.android.gms.maps.model.LatLng

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class MessageItem(val message:String ,val author: String,val unixTimeStamp: Long ,val
    location: LatLng?)
