package com.assignment.youverifytest.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProductTabViewModel: ViewModel() {

    private var pageIndex = 0

    private val _productTabScreenData = mutableStateOf(productTabScreenData())
    val productTabScreenData: ProductTabScreenData?
        get() = _productTabScreenData.value



    private fun productTabScreenData(): ProductTabScreenData {
        return ProductTabScreenData(
            screenType = pageIndex
        )
    }

    fun changeScreen(newPageIndex: Int) {
        pageIndex = newPageIndex
        _productTabScreenData.value = productTabScreenData()
    }

}

data class ProductTabScreenData(
    val screenType: Int
)