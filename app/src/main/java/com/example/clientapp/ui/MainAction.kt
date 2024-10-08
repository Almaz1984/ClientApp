package com.example.clientapp.ui

sealed class MainAction {
    data object RequestLocation : MainAction()

    data object RequestCheckPermission : MainAction()

    data object SettingsClicked : MainAction()

    data object SettingsShown : MainAction()
}
