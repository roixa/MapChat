package com.roix.mapchat.data.repositories.dynamic_links

import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IDynamicLinksRepository {
    fun buildDynamicInviteLink(uuid:Long): Single<String>
}