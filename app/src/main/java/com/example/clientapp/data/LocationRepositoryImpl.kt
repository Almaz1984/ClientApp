package com.example.clientapp.data

import android.location.Location
import com.example.clientapp.data.providers.LocationProvider
import com.example.clientapp.domain.LocationRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl(
    private val locationProvider: LocationProvider,
) : LocationRepository {
    override suspend fun getCurrentLocation(): Location? =
        suspendCoroutine { continuation ->
            locationProvider.getCurrentLocation { location ->
                continuation.resume(location)
            }
        }
}
