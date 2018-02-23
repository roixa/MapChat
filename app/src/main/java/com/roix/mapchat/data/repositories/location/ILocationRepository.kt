package com.roix.mapchat.data.repositories.location

import android.content.Context
import com.roix.mapchat.data.models.GroupItem
import io.reactivex.Completable

interface ILocationRepository {
    fun requestLocationsToGroup(groupItem: GroupItem)
}