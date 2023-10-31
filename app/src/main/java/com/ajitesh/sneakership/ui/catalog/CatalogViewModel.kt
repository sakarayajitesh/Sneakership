package com.ajitesh.sneakership.ui.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CatalogUiState {
    object Loading : CatalogUiState()
    data class ShowSneakerList(val sneakers: List<Sneaker>) : CatalogUiState()
}

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _catalogUiState =
        MutableStateFlow<CatalogUiState>(CatalogUiState.ShowSneakerList(emptyList()))
    val catalogUiState = _catalogUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            homeRepository.fetchData()
            updateCatalogUiState(CatalogUiState.Loading)
            homeRepository.getAllSneakers()
                .combine(searchQuery) { sneakers, search ->
                    if (search.isNotEmpty()) {
                        sneakers.filter { sneaker ->
                            sneaker.title.trim().contains(search, ignoreCase = true)
                        }
                    } else {
                        sneakers
                    }
                }.collect {
                    updateCatalogUiState(CatalogUiState.ShowSneakerList(it))
                }
        }

    }

    private fun updateCatalogUiState(newState: CatalogUiState) {
        _catalogUiState.value = newState
    }

    fun onSearchChange(searchText: String) {
        _searchQuery.value = searchText
        Log.d("Search", searchText)
    }

    fun addToCart(id: String, onTaskCompletion: () -> Unit) {
        viewModelScope.launch {
            homeRepository.addToCart(id)
            onTaskCompletion()
        }
    }
}