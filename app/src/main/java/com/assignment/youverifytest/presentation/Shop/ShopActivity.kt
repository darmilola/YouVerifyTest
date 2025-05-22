package com.assignment.youverifytest.presentation.Shop

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.assignment.youverifytest.domain.models.Product
import com.assignment.youverifytest.domain.models.ProductItemUIModel
import com.assignment.youverifytest.ui.theme.YouVerifyTestTheme
import com.assignment.youverifytest.utils.getProductViewHeight
import com.assignment.youverifytest.viewmodels.LoadingScreenUIStateViewModel
import com.assignment.youverifytest.viewmodels.ProductResourceListEnvelopeViewModel
import com.assignment.youverifytest.widgets.ProductItem
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.craftsilicontest.component.ImageComponent
import com.assignment.craftsilicontest.component.IndeterminateCircularProgressBar
import com.assignment.youverifytest.R
import com.assignment.youverifytest.domain.models.OrderItem
import com.assignment.youverifytest.presentation.MainActivity
import com.assignment.youverifytest.viewmodels.MainViewModel
import com.assignment.youverifytest.widgets.ErrorOccurredWidget
import com.assignment.youverifytest.widgets.ProductDetailBottomSheet
import com.assignment.youverifytest.widgets.TitleWidget
import org.koin.core.component.inject
import presentations.components.TextComponent

class ShopActivity : AppCompatActivity() {

    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel? = null
    @Transient
    private val productPresenter: ProductPresenter = ProductPresenter()
    @Transient
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            loadingScreenUiStateViewModel = viewModel()
            productResourceListEnvelopeViewModel = viewModel()
            mainViewModel = viewModel()

            val productHandler = ShopProductsHandler(
                loadingScreenUiStateViewModel!!,
                productResourceListEnvelopeViewModel!!,
                productPresenter
            )
            productHandler.init()

            productPresenter.getProducts(1)

            YouVerifyTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Column(modifier = Modifier.height(100.dp).fillMaxWidth().systemBarsPadding()) {
                            Row(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                                Box(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {}
                                Box(
                                    modifier = Modifier.weight(2f).fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TitleWidget(textColor = Color.Black, title = "Products")
                                }

                                Box(
                                    modifier = Modifier.weight(1f).fillMaxHeight().padding(end = 10.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    val iconModifier = Modifier
                                        .padding(top = 5.dp)
                                        .clickable {

                                        }
                                        .size(24.dp)
                                   /* ImageComponent(
                                        imageModifier = iconModifier,
                                        imageRes = "drawable/search_icon.png",
                                        colorFilter = ColorFilter.tint(color = Color.Black)
                                    )*/

                                }

                            }
                        }
                    },
                    content = { innerPadding ->
                        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
                        val selectedProduct = remember { mutableStateOf(Product()) }

                        if (showProductDetailBottomSheet) {
                            mainViewModel!!.showProductBottomSheet(true)
                        }
                        else{
                            mainViewModel!!.showProductBottomSheet(false)
                        }

                        if (showProductDetailBottomSheet && selectedProduct.value.productId != -1L) {
                            ProductDetailBottomSheet(
                                mainViewModel!!,
                                OrderItem(itemProduct = selectedProduct.value),
                                onDismiss = {
                                    selectedProduct.value = Product()
                                },
                                onAddToCart = { isAddToCart, item ->
                                    showProductDetailBottomSheet = false
                                })
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(color = Color.White)
                        ) {


                            val columnModifier = Modifier
                                .fillMaxSize()
                            Column(
                                modifier = columnModifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {

                                ProductContent(
                                    productResourceListEnvelopeViewModel!!,
                                    onProductSelected = {
                                        selectedProduct.value = it
                                        showProductDetailBottomSheet = true
                                    })
                            }

                        }
                    },
                    floatingActionButton = {
                        val cartSize = mainViewModel!!.unSavedOrderSize.collectAsState()
                        val cartContainer = if (cartSize.value > 0) 140 else 0
                        Box(
                            modifier = Modifier.size(cartContainer.dp)
                                .padding(bottom = 45.dp), contentAlignment = Alignment.CenterEnd
                        ) {
                            AttachShoppingCartImage(mainViewModel!!, onOpenCart = {})
                        }
                    })
            }
        }

    }

    @Composable
    fun AttachShoppingCartImage(mainViewModel: MainViewModel, onOpenCart:()->Unit) {
        val cart = mainViewModel.unSavedOrders.collectAsState()
        val cartSize = cart.value.size

        val indicatorModifier = Modifier
            .padding(end = 15.dp, bottom = 20.dp)
            .background(color = Color.Transparent)
            .size(30.dp)
            .clip(CircleShape)
            .background(color = Color(color = 0xFFFF5080))

        Box(
            Modifier
                .clip(CircleShape)
                .size(70.dp)
                .clickable {
                   onOpenCart()
                }
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(40.dp)
            ImageComponent(
                imageModifier = modifier,
                imageRes = R.drawable.shopping_cart,
                colorFilter = ColorFilter.tint(color = Color.White)
            )
            Box(
                modifier = indicatorModifier,
                contentAlignment = Alignment.Center
            ) {
                TextComponent(
                    text = cartSize.toString(),
                    fontSize = 17,
                    textStyle = MaterialTheme.typography.titleSmall,
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }

    @Composable
    fun ProductContent(
        productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel,
        onProductSelected: (Product) -> Unit
    ) {

        val uiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()
        if (uiState.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        }
        else if (uiState.value.isFailed) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                    productPresenter.getProducts(1)
                })
            }
        }
        else if (uiState.value.isSuccess) {

            val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
            val selectedProduct = remember { mutableStateOf(Product()) }

            val productUIModel by remember {
                mutableStateOf(
                    ProductItemUIModel(
                        selectedProduct.value,
                        productList.value
                    )
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 70.dp),
                contentAlignment = Alignment.Center
            ) {

                val columnModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = columnModifier
                ) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(
                                getProductViewHeight(productList.value).dp
                            ),
                            contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            userScrollEnabled = true
                        ) {
                            runBlocking {
                                items(productUIModel.productList.size) { it ->
                                    ProductItem(
                                        productUIModel.productList[it],
                                        onProductClickListener = { it2 ->
                                            onProductSelected(it2)
                                            selectedProduct.value = it2
                                        },
                                        onFavClicked = {},
                                        onUnFavClicked = {})
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}