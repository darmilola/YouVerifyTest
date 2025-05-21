package com.assignment.youverifytest.presentation.Shop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.assignment.craftsilicontest.component.IndeterminateCircularProgressBar
import com.assignment.youverifytest.widgets.ErrorOccurredWidget
import org.koin.core.component.inject

class ShopActivity : AppCompatActivity() {

    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel? = null
    @Transient
    private val productPresenter: ProductPresenter = ProductPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            loadingScreenUiStateViewModel = viewModel()
            productResourceListEnvelopeViewModel = viewModel()

            val productHandler = ShopProductsHandler(
                loadingScreenUiStateViewModel!!, productResourceListEnvelopeViewModel!!, productPresenter)
            productHandler.init()

            productPresenter.getProducts(1)

            YouVerifyTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = Color.Yellow)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = Color.Yellow),
                            contentAlignment = Alignment.Center
                        ) {

                            val columnModifier = Modifier
                                .fillMaxSize()
                            Column(
                                modifier = columnModifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {

                                ProductContent(productResourceListEnvelopeViewModel!!, onProductSelected = {})
                            }

                        }
                    }
                }
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