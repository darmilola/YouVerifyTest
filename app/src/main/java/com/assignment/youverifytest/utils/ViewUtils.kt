package com.assignment.youverifytest.utils

import com.assignment.youverifytest.domain.models.Product

fun getProductViewHeight(
    itemList: List<Product>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}