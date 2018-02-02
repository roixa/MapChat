package com.roix.mapchat.data.models

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
data class MessageItem(val message:String ,val author: String,val unixTimeStamp: Long ,val location: Pair<Double, Double>?)
