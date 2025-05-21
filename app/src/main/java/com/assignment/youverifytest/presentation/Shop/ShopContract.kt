package com.assignment.youverifytest.presentation.Shop

import com.assignment.youverifytest.domain.models.AppUIStates
import com.assignment.youverifytest.domain.models.ProductResourceListEnvelope

class ProductContract {
    interface View {
        fun showLce(appUIStates: AppUIStates)
        fun showProducts(products: ProductResourceListEnvelope?)
    }

    abstract class Presenter {
        abstract fun getProducts(vendorId: Long)
        abstract fun registerUIContract(view: View?)
    }
}