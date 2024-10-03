package com.example.clientapp.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityMainBinding
import com.example.clientapp.ui.dialogs.GoToSettingsFragment
import com.example.clientapp.ui.dialogs.RationaleFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupFragmentListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is MainState.Idle -> {
                            // no-op
                        }

                        is MainState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is MainState.CheckPermission -> {
                            checkLocationPermissions()
                        }

                        is MainState.LocationReceived -> {
                            binding.apply {
                                progressBar.isVisible = false
                                latitude.isVisible = true
                                longitude.isVisible = true
                                latitude.text = resources.getString(R.string.latitude, it.location.latitude)
                                longitude.text = resources.getString(R.string.longitude, it.location.longitude)
                            }
                        }

                        is MainState.Error -> {
                            binding.progressBar.isVisible = false
                            showErrorToast()
                        }

                        MainState.GoToSettings -> {
                            openSettings()
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            handleLocationPermissionResult(grantResults)
        }
    }

    private fun showErrorToast() {
        makeText(this, getText(R.string.error_text), LENGTH_SHORT).show()
    }

    private fun handleLocationPermissionResult(grantResults: IntArray) {
        val isPermissionGranted = grantResults.contains(PERMISSION_GRANTED)

        if (isPermissionGranted) {
            viewModel.handleAction(MainAction.RequestLocation)
        } else if (!shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) &&
            !shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)
        ) {
            showSettingsDialog()
        }
    }

    private fun showSettingsDialog() {
        GoToSettingsFragment().show(supportFragmentManager, GoToSettingsFragment.TAG)
    }

    private fun setupFragmentListeners() {
        supportFragmentManager.setFragmentResultListener(RATIONALE_KEY, this) { _, bundle ->
            val isWantToAllowAfterRationale = bundle.getBoolean(RESULT_KEY)
            if (isWantToAllowAfterRationale) {
                viewModel.handleAction(MainAction.CheckPermission)
            }
        }

        supportFragmentManager.setFragmentResultListener(SETTINGS_KEY, this) { _, bundle ->
            val isWantToOpenSettings = bundle.getBoolean(RESULT_KEY)
            if (isWantToOpenSettings) {
                viewModel.handleAction(MainAction.SettingsClicked)
            }
        }
    }

    private fun openSettings() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts(SETTINGS_SCHEME, this.packageName, null))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun checkLocationPermissions() {
        if (hasLocationPermission()) {
            viewModel.handleAction(MainAction.RequestLocation)
        } else {
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) ||
                shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)
            ) {
                showRationaleDialog()
            } else {
                requestLocationPermissions()
            }
        }
    }

    private fun hasLocationPermission(): Boolean =
        checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED ||
            checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    private fun showRationaleDialog() {
        RationaleFragment().show(supportFragmentManager, RationaleFragment.TAG)
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE,
        )
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val SETTINGS_SCHEME = "package"
        const val RATIONALE_KEY = "RATIONALE_KEY"
        const val SETTINGS_KEY = "SETTINGS_KEY"
        const val RESULT_KEY = "RESULT_KEY"
    }
}
