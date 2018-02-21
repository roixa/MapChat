package com.roix.mapchat.data.repositories.location

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.util.Log
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Completable
import javax.inject.Inject
import android.app.PendingIntent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import com.roix.mapchat.R
import com.roix.mapchat.ui.root.views.RootActivity
import android.os.Bundle
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class LocationRepository : ILocationRepository {


    @Inject lateinit var firebaseRepository: FirebaseRepository

    private lateinit var currentGroup: GroupItem

    @Inject lateinit var context:Context

    val locationListener= LocationListener()

    @Inject constructor(context:Context){
        this.context=context
        requestLocationUpdate()
    }

    companion object {
        private val LOCATION_INTERVAL = 10000
        private val LOCATION_DISTANCE = 10f
    }


    override fun requestLocationsToGroup(groupItem: GroupItem): Completable = Completable.create{ e ->
        //requestLocationUpdate()
        currentGroup=groupItem
        e.onComplete()
    }

    private fun requestLocationUpdate(){
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL.toLong(),
                    LOCATION_DISTANCE,
                    locationListener
            )
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL.toLong(),
                    LOCATION_DISTANCE,
                    locationListener
            )

        } catch (ex: java.lang.SecurityException) {
            Log.i(LocationService.TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(LocationService.TAG, "gps provider does not exist " + ex.message)
        }

    }
     class LocationListener() : android.location.LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.e(LocationService.TAG, "onLocationChanged: " + location)
        }

        override fun onProviderDisabled(provider: String) {
            Log.e(LocationService.TAG, "onProviderDisabled: " + provider)
        }

        override fun onProviderEnabled(provider: String) {
            Log.e(LocationService.TAG, "onProviderEnabled: " + provider)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.e(LocationService.TAG, "onStatusChanged: " + provider)
        }
    }


}
