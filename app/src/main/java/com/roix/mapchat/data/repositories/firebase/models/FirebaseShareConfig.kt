package com.roix.mapchat.data.repositories.firebase.models

import com.roix.mapchat.data.common.Parseble
import com.roix.mapchat.data.models.ShareConfig

/**
 * Created by roix on 25.02.2018.
 */
class FirebaseShareConfig : Parseble<ShareConfig> {
    var uuid: Long? = null
    var groupUuid: Long? = null
    var isInvite: Boolean? = null
    var isDetermPersonInfo: Boolean? = null
    var username: String? = null
    var iconPos: Int? = null

    companion object {
        fun from(shareConfig: ShareConfig):FirebaseShareConfig{
            val ret = FirebaseShareConfig()
            ret.uuid=shareConfig.uuid
            ret.groupUuid=shareConfig.groupUuid
            ret.isInvite=shareConfig.isInvite
            ret.isDetermPersonInfo=shareConfig.isDetermPerson
            ret.username=shareConfig.username
            ret.iconPos=shareConfig.iconPos
            return ret
        }
    }

    override fun isValid(): Boolean = uuid != null && groupUuid != null &&
            isInvite != null && isDetermPersonInfo != null && (!isDetermPersonInfo!!
            || (username != null && iconPos != null))

    override fun parse(): ShareConfig = ShareConfig(uuid!!,groupUuid!!,isInvite!!,isDetermPersonInfo!!,username,iconPos)

}