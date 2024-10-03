package com.example.clientapp.data.providers

import android.location.Location

interface LocationProvider {
    fun getCurrentLocation(onLocationReceived: (Location?) -> Unit)
}
