package com.assignment.youverifytest.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.assignment.youverifytest.domain.Enums.DeliveryMethodEnum
import kotlinx.coroutines.flow.StateFlow

class CartViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _subtotal =  savedStateHandle.getStateFlow("subtotal", 0L)
    private var _total =  savedStateHandle.getStateFlow("total", 0L)
    private var _deliveryFee =  savedStateHandle.getStateFlow("deliveryFee", 0L)
    private var _deliveryMethod =  savedStateHandle.getStateFlow("deliveryMethod", DeliveryMethodEnum.PICKUP.toPath())


    val subtotal: StateFlow<Long>
        get() = _subtotal

    val total: StateFlow<Long>
        get() = _total

    val deliveryFee: StateFlow<Long>
        get() = _deliveryFee

    val deliveryMethod: StateFlow<String>
        get() = _deliveryMethod

    fun setSubTotal(subtotal: Long) {
        savedStateHandle["subtotal"] = subtotal
    }

    fun setTotal(total: Long) {
        savedStateHandle["total"] = total
    }

    fun setDeliveryFee(deliveryFee: Long) {
        savedStateHandle["deliveryFee"] = deliveryFee
    }

    fun setDeliveryMethod(deliveryMethod: String) {
        savedStateHandle["deliveryMethod"] = deliveryMethod
    }




}