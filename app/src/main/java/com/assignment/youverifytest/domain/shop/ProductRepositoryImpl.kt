package com.assignment.youverifytest.domain.shop

import com.badoo.reaktive.single.Single
import io.ktor.client.HttpClient
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

class ProductRepositoryImpl(): ProductRepository, KoinComponent {

    private val productNetworkService: ProductNetworkService by inject()

    override suspend fun getAllProducts(
        vendorId: Long
    ): Single<ProductListDataResponse> {
        val param = GetAllProductsRequest(vendorId = vendorId)
        return productNetworkService.getAllProducts(param)
    }
}