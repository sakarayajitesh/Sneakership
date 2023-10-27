package com.ajitesh.sneakership.ui.home

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

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class ShowSneakerList(val sneakers: List<Sneaker>) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeUiState =
        MutableStateFlow<HomeUiState>(HomeUiState.ShowSneakerList(emptyList()))
    val homeUiState = _homeUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            homeRepository.fetchData()
            updateHomeUiState(HomeUiState.Loading)
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
                    updateHomeUiState(HomeUiState.ShowSneakerList(it))
                }
        }

    }

    private fun updateHomeUiState(newState: HomeUiState) {
        _homeUiState.value = newState
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