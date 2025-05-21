package com.assignment.youverifytest.domain.shop

import com.badoo.reaktive.single.toSingle
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class ProductNetworkService(private val apiService: HttpClient) {

    companion object {
        private const val PRODUCT_END_POINT = "https://yesbeauty-wy9j.onrender.com/api/v1/vendor/"
    }

    suspend fun getAllProducts(getAllProductsRequest: GetAllProductsRequest) =
        apiService.post {
            url(PRODUCT_END_POINT+"product/get/all")
            contentType(ContentType.Application.Json)
            setBody(getAllProductsRequest)
        }.body<ProductListDataResponse>().toSingle()

}