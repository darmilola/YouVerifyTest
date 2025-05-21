package com.assignment.youverifytest.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.assignment.youverifytest.domain.models.Product
import kotlinx.coroutines.flow.StateFlow

class ProductResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<Product>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<Product>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: List<Product>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<Product>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }


}
