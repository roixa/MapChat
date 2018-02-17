package com.roix.mapchat.data.repositories.location

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.util.Log
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Completable
import javax.inject.Inject
import android.app.PendingIntent
import android.os.Build
import com.roix.mapchat.R
import com.roix.mapchat.ui.root.views.RootActivity


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class LocationRepository : ILocationRepository {

    val context:Context

    @Inject constructor(context: Context) {
        this.context=context
    }

    override fun sendLocationToGroup(groupItem: GroupItem): Completable = Completable.create{e ->
        Log.e(LocationService.TAG, "onStart service")

        val intent=Intent(context, LocationService::class.java)
        intent.putExtra(LocationService.TAG_GROUP_INTENT,groupItem)
        context.startService(intent)
        e.onComplete()
    }


}
