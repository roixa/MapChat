package com.roix.mapchat.data.models

/**
 * Created by roix on 25.02.2018.
 */
data class ShareConfig(val uuid: Long, val groupUuid: Long, val isInvite: Boolean,
                       val isDetermPerson: Boolean, val username: String?, val iconPos: Int?)
