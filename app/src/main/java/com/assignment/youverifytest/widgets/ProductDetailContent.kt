package com.assignment.youverifytest.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assignment.craftsilicontest.component.AsyncImageComponent
import com.assignment.craftsilicontest.component.ButtonComponent
import com.assignment.craftsilicontest.component.ToggleButton
import com.assignment.youverifytest.domain.Enums.ValuesLimit
import com.assignment.youverifytest.domain.models.OrderItem
import com.assignment.youverifytest.domain.models.Product
import com.assignment.youverifytest.utils.formatNumber
import com.assignment.youverifytest.viewmodels.MainViewModel
import presentations.components.TextComponent

@Composable
fun ProductDetailContent(mainViewModel: MainViewModel, selectedProduct: OrderItem, onAddToCart: (Boolean) -> Unit) {
    val itemKey = (ValuesLimit.MIN_VALUE.toValue() ..ValuesLimit.MAX_VALUE.toValue()).random()
    val currentOrder = mainViewModel.unSavedOrders.collectAsState()
    val orderItem = remember { mutableStateOf(OrderItem()) }

        orderItem.value = OrderItem()
        orderItem.value.itemKey = itemKey
        orderItem.value.itemProduct = selectedProduct.itemProduct
        orderItem.value.productId = selectedProduct.itemProduct?.productId!!


    Scaffold(
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                ProductBottomSheetContent(selectedProduct.itemProduct!!, mainViewModel)
            }
        },
        bottomBar = {
            Column(modifier = Modifier
                .padding(top = 10.dp, bottom = 30.dp)
                .height(80.dp))
            {
                val buttonStyle2 = Modifier
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 4.dp)
                    .background(color = Color.Black)
                    .fillMaxWidth()
                    .height(50.dp)

                val bgStyle = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()

                    Row(
                        modifier = bgStyle,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.50f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            productItemIncrementDecrementWidget(orderItem.value,onItemCountChanged = {
                                orderItem.value.itemCount = it.itemCount
                            }, onItemRemovedFromCart = {})
                        }

                        ButtonComponent(
                            modifier = buttonStyle2,
                            buttonText = "Add to Cart",
                            fontSize = 16,
                            shape = RoundedCornerShape(15.dp),
                            textColor = Color(color = 0xFFFFFFFF),
                            style = TextStyle(),
                            borderStroke = null
                        ) {
                            currentOrder.value.add(orderItem.value)
                            mainViewModel.setUnsavedOrderSize(currentOrder.value.size)
                            mainViewModel.setCurrentUnsavedOrders(currentOrder.value)
                            onAddToCart(true)
                        }
                    }

            }
        }

    )
}


@Composable
fun ProductBottomSheetContent(product: Product, mainViewModel: MainViewModel) {

    var currentTabScreen by remember { mutableStateOf(0) }
    val reviewText = if (product.productReviews?.isNotEmpty() == true) "Reviews"  else "No Reviews"

    val boxModifier =
        Modifier
            .height(350.dp)
            .fillMaxWidth()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
            AttachScrollingProductImages(product)
            ProductBottomSheetContentHeader()
        }
        ProductNameInfoContent(product, "")
        Divider(color = Color(color = 0x90C8C8C8), thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.90f).padding(top = 20.dp))

        ToggleButton(shape = RoundedCornerShape(5.dp), onLeftClicked = {
           currentTabScreen = 0
        }, onRightClicked = {
            currentTabScreen = 1
        }, leftText = "Description", rightText = reviewText)
        ProductTabScreen(product,currentTabScreen)
    }
}



@Composable
fun ProductDescription(product: Product) {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Description",
            fontSize = 18,
            textStyle = MaterialTheme.typography.bodyLarge,
            textColor = Color.Black,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier
                .fillMaxWidth())

        TextComponent(
            textModifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 20.dp), text = product.productDescription,
            fontSize = 16,
            textStyle = MaterialTheme.typography.bodyMedium, textColor = Color.DarkGray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium, lineHeight = 25)
    }

}


@Composable
fun ProductNameInfoContent(product: Product, currencyUnit: String) {
    Row(modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .fillMaxWidth(0.7f)) {
            ProductTitle(product)
        }
        ProductPriceInfoContent(product, currencyUnit)
    }
}

@Composable
fun ProductPriceInfoContent(product: Product, currencyUnit: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ) {

        TextComponent(
            text = "$currencyUnit${formatNumber(product.productPrice)}",
            fontSize = 20,
            textStyle = TextStyle(),
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            textColor = Color.Black,
            maxLines = 1,
            textModifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun ProductBottomSheetContentHeader() {
    Row(modifier = Modifier.height(30.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterStart) {

        }
        Box(Modifier.weight(3f), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black)
                    .height(4.dp)
                    .width(60.dp)
            )
        }
        Box(modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterEnd) {

        }
    }
}

@Composable
fun ProductTitle(product: Product){
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = product.productName,
            fontSize = 18,
            textStyle = MaterialTheme.typography.titleMedium,
            textColor = Color.Black,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            maxLines = 2,
            textModifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttachScrollingProductImages(product: Product){

    val productImages = product.productImages
    val pagerState = rememberPagerState(pageCount = {
        productImages.size
    })

    val  boxModifier =
        Modifier
            .padding(bottom = 20.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    // AnimationEffect
    Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImageComponent(imageModifier = Modifier.fillMaxWidth().height(350.dp), imageRes = productImages[page].imageUrl, contentScale = ContentScale.Crop)
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 4.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                var color = Color.White
                var width = 0
                if (pagerState.currentPage == iteration){
                    color =  Color.Black
                    width = 20
                } else{
                    color =  Color.LightGray
                    width = 20
                }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(3.dp)
                        .width(width.dp)
                )
            }

        }
    }

}