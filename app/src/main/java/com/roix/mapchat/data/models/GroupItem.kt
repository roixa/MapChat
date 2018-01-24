package com.roix.mapchat.data.models

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class GroupItem(val name: String, val descr: String, val users: List<User>,val ownerUUid:Long)
