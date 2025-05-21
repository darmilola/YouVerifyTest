package com.assignment.youverifytest.domain.shop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllProductsRequest(@SerialName("vendorId") val vendorId: Long)