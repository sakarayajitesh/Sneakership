package com.ajitesh.sneakership

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajitesh.sneakership.ui.MainScreen
import com.ajitesh.sneakership.ui.cart.CartScreen
import com.ajitesh.sneakership.ui.cart.CartViewModel
import com.ajitesh.sneakership.ui.catalog.CatalogScreen
import com.ajitesh.sneakership.ui.catalog.CatalogViewModel
import com.ajitesh.sneakership.ui.detail.DetailScreen
import com.ajitesh.sneakership.ui.detail.DetailViewModel
import com.ajitesh.sneakership.ui.theme.SneakershipTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SneakershipTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {
                        composable("main") {
                            MainScreen(navController::navigate)
                        }
                        composable("catalog") {
                            val catalogViewModel = hiltViewModel<CatalogViewModel>()
                            val homeUiState by catalogViewModel.catalogUiState.collectAsState()
                            val searchText by catalogViewModel.searchQuery.collectAsState()
                            CatalogScreen(
                                uiState = homeUiState,
                                searchText = searchText,
                                searchTextChange = catalogViewModel::onSearchChange,
                                addToCart = catalogViewModel::addToCart,
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
            }
        }
    }
}