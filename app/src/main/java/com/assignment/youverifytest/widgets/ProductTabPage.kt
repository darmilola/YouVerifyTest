package com.assignment.youverifytest.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import com.assignment.youverifytest.domain.models.Product
import com.assignment.youverifytest.viewmodels.ProductTabViewModel

@Composable
fun ProductTabScreen(product: Product, currentPosition: Int = 0) {

    val viewModel = ProductTabViewModel()
    viewModel.changeScreen(currentPosition)
    val state = viewModel.productTabScreenData!!.screenType

    AnimatedContent(targetState = state) { targetState: Int ->
        when (targetState) {
            0 -> {
                   ProductDescription(product)
            }
            1 -> {
                if (product.productReviews!!.isNotEmpty()) {
                    AttachProductReviews(product)
                }
            }
        }
    }

}
