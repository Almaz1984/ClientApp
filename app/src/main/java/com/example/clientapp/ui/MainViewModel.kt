package com.example.clientapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.domain.LocationRepository
import com.example.clientapp.ui.MainState.CheckPermission
import com.example.clientapp.ui.MainState.GoToSettings
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
        handleAction(MainAction.RequestCheckPermission)
    }

    fun handleAction(action: MainAction) {
        when (action) {
            MainAction.RequestLocation -> getLocation()
            MainAction.RequestCheckPermission -> _state.value = CheckPermission
            MainAction.SettingsClicked -> _state.value = GoToSettings
            MainAction.SettingsShown -> _state.value = Idle
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
