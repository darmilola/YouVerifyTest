package com.assignment.youverifytest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProductImages(@SerialName("id") val id: Long?, @SerialName("product_id") val productId: Long?, @SerialName("imageUrl") val imageUrl: String = ""): Parcelable