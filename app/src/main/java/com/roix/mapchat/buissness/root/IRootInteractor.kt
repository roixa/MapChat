package com.roix.mapchat.buissness.root

import com.roix.mapchat.data.models.GroupItem
import io.reactivex.Completable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface IRootInteractor{
    fun enterToGroup(groupItem: GroupItem):Completable
}
