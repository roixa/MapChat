package com.roix.mapchat.data.repositories.icons

import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IIconsRepository {
    fun getMarkersIcons():Single<List<IconItem>>
    fun getUsersIcons():Single<List<IconItem>>
}