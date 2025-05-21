package com.assignment.youverifytest.domain.shop

import com.assignment.youverifytest.domain.models.ProductResourceListEnvelope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductListDataResponse(@SerialName("response") var listItem: ProductResourceListEnvelope = ProductResourceListEnvelope(), @SerialName("status")  var status: String)