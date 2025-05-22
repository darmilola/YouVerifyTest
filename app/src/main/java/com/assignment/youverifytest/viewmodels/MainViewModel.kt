package com.assignment.youverifytest.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.assignment.youverifytest.domain.models.OrderItem
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(val savedStateHandle: SavedStateHandle): ViewModel(){
    private var _showProductBottomSheet =  savedStateHandle.getStateFlow("showProductBottomSheet", false)
    private var _currentUnsavedOrders =  savedStateHandle.getStateFlow("currentUnsavedOrders", ArrayList<OrderItem>())
    private var _currentUnsavedOrderSize =  savedStateHandle.getStateFlow("currentUnsavedOrderSize", 0)

    fun showProductBottomSheet(show: Boolean) {
        savedStateHandle["showProductBottomSheet"] = show
    }

    fun setCurrentUnsavedOrders(orderItems: ArrayList<OrderItem>) {
        savedStateHandle["currentUnsavedOrders"] = orderItems
    }

    val showProductBottomSheet: StateFlow<Boolean>
        get() = _showProductBottomSheet

    val unSavedOrders: StateFlow<ArrayList<OrderItem>>
        get() = _currentUnsavedOrders

    val unSavedOrderSize: StateFlow<Int>
        get() = _currentUnsavedOrderSize

    fun setUnsavedOrderSize(size: Int) {
        savedStateHandle["currentUnsavedOrderSize"] = size
    }

}