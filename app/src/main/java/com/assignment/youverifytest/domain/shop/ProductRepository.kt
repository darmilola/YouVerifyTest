package com.assignment.youverifytest.domain.shop

import com.badoo.reaktive.single.Single

interface ProductRepository {
    suspend fun getAllProducts(vendorId: Long): Single<ProductListDataResponse>
}
