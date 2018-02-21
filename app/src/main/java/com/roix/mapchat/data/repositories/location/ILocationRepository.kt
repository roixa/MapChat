package com.roix.mapchat.data.repositories.location

import com.roix.mapchat.data.models.GroupItem
import io.reactivex.Completable

interface ILocationRepository {
    fun requestLocationsToGroup(groupItem: GroupItem):Completable
}