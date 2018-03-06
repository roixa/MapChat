package com.roix.mapchat.data.repositories.location

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.toothpick.common.ApplicationScope
import com.roix.mapchat.utils.rx.general.RxSchedulersAbs
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class LocationRepository : ILocationRepository, LocationListener {

    @Inject lateinit var firebaseRepository: FirebaseRepository

    var context: Context
    @Inject lateinit var rxScheduler: RxSchedulersAbs

    private lateinit var currentGroup: GroupItem

    @Inject constructor(context:Context){
        this.context=context
        context.startService(Intent(context,LocationService::class.java))
    }


    companion object {
        private val LOCATION_INTERVAL = 10000
        private val LOCATION_DISTANCE = 10f
    }


    override fun requestLocationsToGroup(groupItem: GroupItem) {
        requestLocationUpdate(context)
        currentGroup = groupItem
    }

    private fun requestLocationUpdate(context: Context) {
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL.toLong(),
                    LOCATION_DISTANCE,
                    this
            )
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL.toLong(),
                    LOCATION_DISTANCE,
                    this
            )

        } catch (ex: java.lang.SecurityException) {
            Log.i(LocationService.TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(LocationService.TAG, "gps provider does not exist " + ex.message)
        }

    }

    override fun onLocationChanged(location: Location) {
        Log.e(LocationService.TAG, "onLocationChanged: " + location)
        firebaseRepository.updateUserPosition(currentGroup, LatLng(location.latitude,location.longitude))
                .compose(rxScheduler.getIoToMainTransformerCompletable())
                .subscribe {

                }
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
