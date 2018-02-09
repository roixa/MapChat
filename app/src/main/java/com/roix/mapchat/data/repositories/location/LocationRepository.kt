package com.roix.mapchat.data.repositories.location

import android.content.Context
import android.content.Intent
import android.util.Log
import com.roix.mapchat.toothpick.common.ApplicationScope
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class LocationRepository : ILocationRepository {
    @Inject constructor(context: Context) {
        Log.e(LocationService.TAG, "constructor  LocationRepository")
        context.startService(Intent(context, LocationService::class.java))

    }


    init {
        Log.e(LocationService.TAG, "init  LocationRepository")

    }


}
