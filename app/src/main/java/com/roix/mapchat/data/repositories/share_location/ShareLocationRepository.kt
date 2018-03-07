package com.roix.mapchat.data.repositories.share_location

import android.content.Context
import android.content.Intent
import com.roix.mapchat.data.repositories.location.LocationService
import com.roix.mapchat.toothpick.common.ApplicationScope
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class ShareLocationRepository : IShareLocationRepository {

    val context :Context

    @Inject constructor(context: Context){
        this.context=context
        context.startService(Intent(context, LocationService::class.java))
    }



}
