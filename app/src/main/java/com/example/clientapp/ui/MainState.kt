package com.example.clientapp.ui

import android.location.Location

sealed class MainState {
    data object Idle : MainState()

    data object Loading : MainState()

    data class LocationReceived(
        val location: Location,
    ) : MainState()

    data object Error : MainState()

    data object CheckPermission : MainState()

    data object GoToSettings : MainState()
}
