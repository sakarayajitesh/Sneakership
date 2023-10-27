package com.ajitesh.sneakership.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailUiState{
    object Loading: DetailUiState()
    data class ShowSneaker(val sneaker: Sneaker): DetailUiState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val sneakerId: String = checkNotNull(savedStateHandle["sneakerId"])

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val sneaker = detailRepository.getSneakerById(sneakerId)
            updateUiState(DetailUiState.ShowSneaker(sneaker))
        }
    }

    private fun updateUiState(newUiState: DetailUiState){
        _detailUiState.value = newUiState
    }

    fun addToCart(id: String, onTaskCompletion: () -> Unit) {
        viewModelScope.launch {
            detailRepository.addToCart(id)
            onTaskCompletion()
        }
    }

}