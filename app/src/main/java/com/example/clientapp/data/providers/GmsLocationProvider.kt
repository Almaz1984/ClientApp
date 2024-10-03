package com.example.clientapp.data.providers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class GmsLocationProvider(
    private val context: Context,
) : LocationProvider {
    private val currentLocationRequest =
        CurrentLocationRequest
            .Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            .build()

    private val cancellationToken = CancellationTokenSource().token

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(onLocationReceived: (Location?) -> Unit) {
        val gmsLocationClient = LocationServices.getFusedLocationProviderClient(context)
        gmsLocationClient
            .getCurrentLocation(currentLocationRequest, cancellationToken)
            .addOnSuccessListener { location: Location? ->
                onLocationReceived(location)
            }.addOnFailureListener {
                onLocationReceived(null)
            }
    }
}
