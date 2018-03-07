package com.roix.mapchat.data.models

import java.io.Serializable

/**
 * Created by roix on 23.01.2018.
 */
data class User(val uid: Long, val groupOwnerUuid: Long, val name: String, val iconPos: Int) : Serializable {

}