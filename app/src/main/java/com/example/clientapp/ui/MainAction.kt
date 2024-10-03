package com.example.clientapp.ui

sealed class MainAction {
    data object RequestLocation : MainAction()

    data object CheckPermission : MainAction()

    data object SettingsClicked : MainAction()
}
