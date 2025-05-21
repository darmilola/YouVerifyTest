package com.assignment.youverifytest.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.assignment.youverifytest.domain.models.AppUIStates
import kotlinx.coroutines.flow.StateFlow

// To be used for initializing the page been loaded, displaying loader with empty screen
class LoadingScreenUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("loadingScreenUiState", AppUIStates())
    val uiStateInfo: StateFlow<AppUIStates>
        get() = _uiState

    fun switchScreenUIState(appUIStates: AppUIStates) {
        savedStateHandle["loadingScreenUiState"] = appUIStates
    }
}