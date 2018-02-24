package com.roix.mapchat.buissness.share

import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IShareInteractor{
    fun getItems(): Single<List<IconItem>>
}
