package com.roix.mapchat.buissness.share

import com.roix.mapchat.data.models.ShareConfig
import com.roix.mapchat.data.repositories.dynamic_links.DynamicLinksRepository
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.data.repositories.icons.IconsRepository
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ShareInteractor : IShareInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var iconRepository: IconsRepository
    @Inject lateinit var dynamicLinksRepository: DynamicLinksRepository


    override fun getItems(): Single<List<IconItem>> = iconRepository.getUsersIcons()

    override fun postShareConfig(shareConfig: ShareConfig): Single<String> = firebaseRepository.postShareConfig(shareConfig)
            .andThen(dynamicLinksRepository.buildDynamicInviteLink(shareConfig.uuid))

}
