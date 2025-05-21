package com.assignment.youverifytest.presentation.Shop

import androidx.room.RoomDatabase
import com.assignment.youverifytest.domain.Enums.ServerResponse
import com.assignment.youverifytest.domain.models.AppUIStates
import com.assignment.youverifytest.domain.shop.ProductRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe

class ProductPresenter(): ProductContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProductContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl()


    override fun registerUIContract(view: ProductContract.View?) {
        contractView = view
    }


    override fun getProducts(vendorId: Long) {
        contractView?.showLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getAllProducts(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showProducts(
                                            result.listItem)
                                    }

                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch (e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

}