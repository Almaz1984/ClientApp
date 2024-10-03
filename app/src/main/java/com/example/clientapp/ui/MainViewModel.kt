package com.example.clientapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.domain.LocationRepository
import com.example.clientapp.ui.MainState.Idle
import com.example.clientapp.ui.MainState.Loading
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleAction(MainAction.CheckPermission)
    }

    fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.RequestLocation -> getLocation()
            is MainAction.CheckPermission -> _state.value = MainState.CheckPermission
            is MainAction.SettingsClicked -> _state.value = MainState.GoToSettings
        }
    }

    private fun getLocation() {
        _state.value = Loading
        viewModelScope.launch {
            try {
                val location = locationRepository.getCurrentLocation()

                _state.value =
                    if (location != null) {
                        MainState.LocationReceived(location)
                    } else {
                        MainState.Error
                    }
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    _state.value = MainState.Error
                }
            }
        }
    }
}
