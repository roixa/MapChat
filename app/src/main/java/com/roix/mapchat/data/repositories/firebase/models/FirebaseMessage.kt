package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.MessageItem

/**
 * Created by belyalov on 01.02.2018.
 */
data class FirebaseMessage(var message: String?, var author: String?, var unixTimeStamp: Long?, var location: Pair<Double, Double>?) : Parseble<MessageItem> {

    constructor() : this("", "", 0, null)

    override fun isValid(): Boolean = message != null && author != null && unixTimeStamp != null

    override fun parse(): MessageItem = MessageItem(message!!, author!!, unixTimeStamp!!, location)

}