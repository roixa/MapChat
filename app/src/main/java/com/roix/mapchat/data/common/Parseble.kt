package com.roix.mapchat.data.common

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

interface Parseble<T> {
    fun isValid(): Boolean
    fun parse(): T
}
