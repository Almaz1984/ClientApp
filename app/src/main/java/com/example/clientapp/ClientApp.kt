package com.example.clientapp

import android.app.Application
import com.example.clientapp.data.LocationRepositoryImpl
import com.example.clientapp.data.providers.FallbackLocationProvider
import com.example.clientapp.data.providers.GmsLocationProvider
import com.example.clientapp.data.providers.HmsLocationProvider
import com.example.clientapp.data.providers.LocationProvider
import com.example.clientapp.domain.LocationRepository
import com.example.clientapp.ui.MainViewModel
import com.example.clientapp.utils.areGmsServicesAvailable
import com.example.clientapp.utils.areHmsServicesAvailable
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class ClientApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ClientApp)
            modules(appModule)
        }
    }
}

val appModule =
    module {
        viewModel { MainViewModel(get()) }
        single<LocationRepository> { LocationRepositoryImpl(get()) }
        single<LocationProvider> {
            when {
                androidContext().areGmsServicesAvailable() -> GmsLocationProvider(get())
                androidContext().areHmsServicesAvailable() -> HmsLocationProvider(get())
                else -> FallbackLocationProvider()
            }
        }
    }
