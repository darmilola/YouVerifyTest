package com.assignment.youverifytest.presentation.Shop

import com.assignment.youverifytest.domain.models.AppUIStates
import com.assignment.youverifytest.domain.models.ProductResourceListEnvelope
import com.assignment.youverifytest.viewmodels.LoadingScreenUIStateViewModel
import com.assignment.youverifytest.viewmodels.ProductResourceListEnvelopeViewModel

class ShopProductsHandler(
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel,
    private val productPresenter: ProductPresenter
) : ProductContract.View {

    fun init() {
        productPresenter.registerUIContract(this)
    }

    override fun showLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showProducts(products: ProductResourceListEnvelope?) {
            productResourceListEnvelopeViewModel.clearData(mutableListOf())
            productResourceListEnvelopeViewModel.setResources(products!!.resources)
            products.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products.totalItemCount?.let {
                productResourceListEnvelopeViewModel.setTotalItemCount(
                    it
                )
            }
            products.displayedItemCount?.let {
                productResourceListEnvelopeViewModel.setDisplayedItemCount(
                    it
                )
            }
    }

}