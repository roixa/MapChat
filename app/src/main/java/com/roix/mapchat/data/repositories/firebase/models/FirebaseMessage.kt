package com.roix.mapchat.data.repositories.firebase.models

import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.MessageItem

/**
 * Created by belyalov on 01.02.2018.
 */
data class FirebaseMessage(var message: String?, var author: String?, var unixTimeStamp: Long?,
                           var longitude: Double?,
                           var lattitude: Double?) : Parseble<MessageItem> {

    constructor() : this("", "", 0, null, null)

    override fun isValid(): Boolean = message != null && author != null && unixTimeStamp != null

    override fun parse(): MessageItem {
        if (lattitude == null || longitude == null) {
            return MessageItem(message!!, author!!, unixTimeStamp!!, null)
        }
        return MessageItem(message!!, author!!, unixTimeStamp!!, LatLng(lattitude!!, longitude!!))
    }

}