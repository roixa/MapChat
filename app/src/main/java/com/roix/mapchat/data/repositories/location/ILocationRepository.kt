package com.roix.mapchat.data.repositories.location

import com.roix.mapchat.data.models.GroupItem

interface ILocationRepository {
    fun requestLocationsToGroup(groupItem: GroupItem)
    fun stopRequestingLocations()
}