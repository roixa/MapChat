package com.roix.mapchat.data.repositories.location

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.utils.rx.general.RxSchedulersAbs
import toothpick.Toothpick
import javax.inject.Inject


/**
 * Created by belyalov on 09.02.2018.
 */
class LocationService : Service(), LocationListener {

    @Inject lateinit var firebaseRepository: FirebaseRepository
    @Inject lateinit var context: Context
    @Inject lateinit var rxScheduler: RxSchedulersAbs

    private var currentGroup: GroupItem?=null

    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        requestLocationUpdate(context)
        currentGroup=intent?.getSerializableExtra("group") as GroupItem?
        currentGroup.apply {
            if (this!=null){
                startForeground(6,buildPendingNotification(this as GroupItem))
            }else{
                stopForeground(true)
            }
        }
        return START_STICKY
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate")
        val scope = Toothpick.openScope(application)
        Toothpick.inject(this, scope)
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    private fun buildPendingNotification(groupItem: GroupItem): Notification {
        val mBuilder = NotificationCompat.Builder(this, "6")
                .setSmallIcon(R.drawable.ic_directions_walk_black)
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentText(groupItem.name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return mBuilder.build()
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
        currentGroup.apply {
            if(this!=null){
                firebaseRepository.updateUserPosition(this, LatLng(location.latitude,location.longitude))
                        .compose(rxScheduler.getIoToMainTransformerCompletable())
                        .subscribe {

                        }

            }
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


    companion object {
        val TAG = "BOOMBOOMTESTGPS"
        val TAG_GROUP_INTENT = "roix_group"
        private val OUTGOING_NOTIFICATION_ID = 1
        private val LOCATION_INTERVAL = 10
        private val LOCATION_DISTANCE = 10f
    }
}
