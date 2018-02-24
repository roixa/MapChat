package com.roix.mapchat.buissness.share

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

    override fun getItems(): Single<List<IconItem>> = iconRepository.getUsersIcons()

}
