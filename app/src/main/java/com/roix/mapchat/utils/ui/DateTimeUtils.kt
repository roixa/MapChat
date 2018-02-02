package com.roix.mapchat.utils.ui

import java.util.*

/**
 * Created by roix on 02.02.2018.
 */
object DateTimeUtils {
    private val SERVER_DATE_FORMAT = SimpleSafeDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault())

    fun convertToDateFormat(unixTimeStamp:Long):String = SERVER_DATE_FORMAT.get().format(unixTimeStamp)
}