package com.roix.mapchat.data.repositories.location

import android.content.Context
import android.content.Intent
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
class LocationRepository : ILocationRepository {

    @Inject lateinit var firebaseRepository: FirebaseRepository

    var context: Context
    @Inject lateinit var rxScheduler: RxSchedulersAbs

    private lateinit var currentGroup: GroupItem

    @Inject constructor(context:Context){
        this.context=context
        sendCommandToService(null)
    }

    override fun requestLocationsToGroup(groupItem: GroupItem) {
        sendCommandToService(groupItem)
        currentGroup = groupItem
    }

    override fun stopRequestingLocations() {
        sendCommandToService(null)
    }

    private fun sendCommandToService(groupItem: GroupItem?){
        val intent=Intent(context,LocationService::class.java)
        if(groupItem!=null){
            intent.putExtra("group",groupItem)
        }
        context.startService(intent)
    }




}
