package com.example.clientapp.domain

import android.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
}
