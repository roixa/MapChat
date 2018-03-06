package com.roix.mapchat.data.repositories.location

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.ui.root.views.RootActivity
import toothpick.Toothpick


/**
 * Created by belyalov on 09.02.2018.
 */
class LocationService : Service() {

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")

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
