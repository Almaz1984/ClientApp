package com.example.clientapp.ui

import android.location.Location
import com.example.clientapp.domain.LocationRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val locationRepository: LocationRepository = mock()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(locationRepository)
    }

    @Test
    fun `should change state to CheckPermission when CheckPermission action is handled`() {
        viewModel.handleAction(MainAction.RequestCheckPermission)

        assertEquals(MainState.CheckPermission, viewModel.state.value)
    }

    @Test
    fun `should change state to Loading when RequestLocation action is handled`() {
        viewModel.handleAction(MainAction.RequestLocation)

        assertEquals(MainState.Loading, viewModel.state.value)
    }

    @Test
    fun `should fetch location and update state to LocationReceived when getCurrentLocation is successful`() =
        runTest {
            val fakeLocation: Location = mock()
            whenever(locationRepository.getCurrentLocation()).thenReturn(fakeLocation)

            viewModel.handleAction(MainAction.RequestLocation)

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(MainState.LocationReceived(fakeLocation), viewModel.state.value)
        }

    @Test
    fun `should update state to Error when getCurrentLocation throws Exception`() =
        runTest {
            whenever(locationRepository.getCurrentLocation()).thenThrow(RuntimeException("Error fetching location"))

            viewModel.handleAction(MainAction.RequestLocation)

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(MainState.Error, viewModel.state.value)
        }
}
