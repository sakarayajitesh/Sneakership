package com.ajitesh.sneakership.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CartUiState {
    data class ShowCartList(val cartList: List<Sneaker>) : CartUiState()
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartUiState = MutableStateFlow<CartUiState>(CartUiState.ShowCartList(emptyList()))
    val cartUiState = _cartUiState.asStateFlow()

    init {
        viewModelScope.launch {
            cartRepository.getAll().collect {
                updateState(CartUiState.ShowCartList(it))
            }
        }
    }

    private fun updateState(newState: CartUiState) {
        _cartUiState.value = newState
    }

    fun deleteFromCart(id: String, onTaskCompletion: () -> Unit) {
        viewModelScope.launch {
            cartRepository.delete(id)
            onTaskCompletion()
        }
    }
}