package com.example.clientapp.data.providers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.huawei.hms.location.LocationCallback
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationResult
import com.huawei.hms.location.LocationServices

class HmsLocationProvider(
    private val context: Context,
) : LocationProvider {
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(onLocationReceived: (Location?) -> Unit) {
        val hmsLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest =
            LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

        hmsLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.locations?.lastOrNull()?.let { location ->
                        onLocationReceived(location)
                        hmsLocationClient.removeLocationUpdates(this)
                    } ?: onLocationReceived(null)
                }
            },
            Looper.getMainLooper(),
        )
    }
}
