package com.ajitesh.sneakership.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajitesh.sneakership.ui.cart.CartScreen
import com.ajitesh.sneakership.ui.cart.CartViewModel
import com.ajitesh.sneakership.ui.detail.DetailScreen
import com.ajitesh.sneakership.ui.detail.DetailViewModel
import com.ajitesh.sneakership.ui.home.HomeScreen
import com.ajitesh.sneakership.ui.home.HomeViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val homeUiState by homeViewModel.homeUiState.collectAsState()
            val searchText by homeViewModel.searchQuery.collectAsState()
            HomeScreen(
                uiState = homeUiState,
                searchText = searchText,
                searchTextChange = homeViewModel::onSearchChange,
                addToCart = homeViewModel::addToCart,
                navigate = navController::navigate
            )
        }
        composable("detail/{sneakerId}") {
            val detailViewModel = hiltViewModel<DetailViewModel>()
            val detailUiState by detailViewModel.detailUiState.collectAsState()
            DetailScreen(
                uiState = detailUiState,
                navigateBack = navController::popBackStack,
                addToCart = detailViewModel::addToCart
            )
        }
        composable("cart") {
            val cartViewModel = hiltViewModel<CartViewModel>()
            val cartUiState by cartViewModel.cartUiState.collectAsState()
            CartScreen(
                uiState = cartUiState,
                deleteFromCart = cartViewModel::deleteFromCart,
                navigateBack = navController::popBackStack
            )
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}