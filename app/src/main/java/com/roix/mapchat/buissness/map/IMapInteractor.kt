package com.roix.mapchat.buissness.map

import android.graphics.drawable.Icon
import com.roix.mapchat.data.repositories.icons.models.IconItem
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface IMapInteractor {
    fun getItems(): Single<List<IconItem>>
}
