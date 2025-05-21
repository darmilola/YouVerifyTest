package com.assignment.youverifytest.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductImages(@SerialName("id") val id: Long?, @SerialName("product_id") val productId: Long?, @SerialName("imageUrl") val imageUrl: String = "")