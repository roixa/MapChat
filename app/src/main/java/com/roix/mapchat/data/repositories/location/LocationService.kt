package com.roix.mapchat.data.repositories.location

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import com.roix.mapchat.ui.root.views.RootActivity
import toothpick.Toothpick
import javax.inject.Inject


/**
 * Created by belyalov on 09.02.2018.
 */
class LocationService : Service() {
    @Inject lateinit var mLocationManager: LocationManager

    @Inject lateinit var firebaseRepository: FirebaseRepository

    private lateinit var currentGroup: GroupItem

    private var mLocationListeners = arrayOf(LocationListener(LocationManager.GPS_PROVIDER),
            LocationListener(LocationManager.NETWORK_PROVIDER))

    private inner class LocationListener(provider: String) : android.location.LocationListener {
        internal var mLastLocation: Location

        init {
            Log.e(TAG, "LocationListener " + provider)
            mLastLocation = Location(provider)
        }

        override fun onLocationChanged(location: Location) {
            Log.e(TAG, "onLocationChanged: " + location)
            mLastLocation.set(location)
        }

        override fun onProviderDisabled(provider: String) {
            Log.e(TAG, "onProviderDisabled: " + provider)
        }

        override fun onProviderEnabled(provider: String) {
            Log.e(TAG, "onProviderEnabled: " + provider)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.e(TAG, "onStatusChanged: " + provider)
        }
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)
        val group = intent?.extras?.getParcelable<GroupItem>(TAG_GROUP_INTENT)
        Log.e(TAG, "on receive intent " + intent?.getStringExtra("test"))

        if (group != null) {
            currentGroup = group
            startForeground(OUTGOING_NOTIFICATION_ID, buildPendingNotification(currentGroup))
        }
        return START_STICKY
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate")
        val scope = Toothpick.openScope(application)
        Toothpick.inject(this, scope)
        try {
            mLocationManager!!.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                    mLocationListeners[1])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "network provider does not exist, " + ex.message)
        }

        try {
            mLocationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                    mLocationListeners[0])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "gps provider does not exist " + ex.message)
        }

    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
        if (mLocationManager != null) {
            for (i in mLocationListeners.indices) {
                try {
                    mLocationManager!!.removeUpdates(mLocationListeners[i])
                } catch (ex: Exception) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex)
                }

            }
        }
    }

    private fun buildPendingNotification(groupItem: GroupItem): Notification {
        /*
        val notificationIntent = Intent(applicationContext, RootActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)

        val notification = Notification.Builder(applicationContext)
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentText(groupItem.name)
                .build()

        notification.contentIntent = pendingIntent
        */
        val notification = Notification(R.drawable.ic_add_white, "dadad",
                System.currentTimeMillis())
        val notificationIntent = Intent(this, RootActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        return notification
    }

    companion object {
        val TAG = "BOOMBOOMTESTGPS"
        val TAG_GROUP_INTENT = "roix_group"
        private val OUTGOING_NOTIFICATION_ID = 1
        private val LOCATION_INTERVAL = 10
        private val LOCATION_DISTANCE = 10f
    }
}
