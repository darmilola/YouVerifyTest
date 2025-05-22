package com.assignment.youverifytest.utils

import com.assignment.youverifytest.domain.models.Product
import java.text.DecimalFormat

fun getProductViewHeight(
    itemList: List<Product>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}

fun formatNumber(number: Long): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(number)
}