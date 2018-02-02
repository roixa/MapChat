package com.roix.mapchat.utils.ui

import java.text.SimpleDateFormat
import java.util.Locale


/**
 * Created by roix on 02.02.2018.
 */
class SimpleSafeDateFormat(private val mPattern: String,  private val mLocale: Locale) : ThreadLocal<SimpleDateFormat>() {

    override fun initialValue(): SimpleDateFormat {
        return SimpleDateFormat(mPattern, mLocale)
    }
}
