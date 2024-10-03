package com.example.clientapp.utils

import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.ConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability

fun Context.areGmsServicesAvailable(): Boolean {
    val availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
    return availability == com.google.android.gms.common.ConnectionResult.SUCCESS
}

fun Context.areHmsServicesAvailable(): Boolean {
    val availability = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(this)
    return availability == ConnectionResult.SUCCESS
}
