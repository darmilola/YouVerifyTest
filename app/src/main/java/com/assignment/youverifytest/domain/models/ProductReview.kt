package com.assignment.youverifytest.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductReview(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("vendor_id")
    val vendorId: Long? = null,
    @SerialName("product_id")
    val productId: Long? = null,
    @SerialName("reviewer")
    val productReviewer: User? = null,
    @SerialName("reviewText")
    val reviewText: String? = null,
    @SerialName("created_at")
    val reviewDate: String? = null)
