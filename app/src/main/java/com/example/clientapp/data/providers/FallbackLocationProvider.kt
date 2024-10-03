package com.example.clientapp.data.providers

import android.location.Location
import android.util.Log

class FallbackLocationProvider : LocationProvider {
    override fun getCurrentLocation(onLocationReceived: (Location?) -> Unit) {
        Log.w("LocationProvider", "Location services are not available")
        onLocationReceived(null)
        return
    }
}
