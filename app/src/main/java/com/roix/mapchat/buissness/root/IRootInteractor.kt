package com.roix.mapchat.buissness.root

import com.roix.mapchat.data.models.GroupItem
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface IRootInteractor{
    fun proceedReceiveDeepLink(groupUuid:Long):Single<Pair<Boolean, GroupItem>>
}
