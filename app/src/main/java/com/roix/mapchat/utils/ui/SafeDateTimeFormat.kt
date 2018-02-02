package com.roix.mapchat.utils.ui

import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * Created by roix on 02.02.2018.
 */
class SafeDateTimeFormat(private val mPattern: String) : ThreadLocal<DateTimeFormatter>() {

    override fun initialValue(): DateTimeFormatter {
        val formatter = DateTimeFormat.forPattern(mPattern)
        formatter.withZone(DateTimeZone.getDefault())
        return formatter
    }
}
