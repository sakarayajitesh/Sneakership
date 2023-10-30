package com.ajitesh.sneakership.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.ajitesh.sneakership.ui.saved.SavedScreen
import com.ajitesh.sneakership.ui.search.SearchScreen

sealed class BottomNavigationObject(val title: String, val icon: ImageVector) {
    object Home : BottomNavigationObject(title = "home", icon = Icons.Outlined.Home)
    object Search : BottomNavigationObject(title = "search", icon = Icons.Outlined.Search)
    object Saved : BottomNavigationObject(title = "saved", icon = Icons.Outlined.Star)
}

val bottomNavigationItems =
    listOf(BottomNavigationObject.Home, BottomNavigationObject.Search, BottomNavigationObject.Saved)


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }


    Scaffold(
        bottomBar = {
            BottomNavigation {
                bottomNavigationItems.onEachIndexed { index, s ->
                    BottomNavigationItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(s.title)
                        },
                        icon = {
                            Icon(
                                imageVector = s.icon,
                                contentDescription = s.title
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = bottomNavigationItems[selectedItem].title,
            modifier = Modifier.padding(innerPadding)
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
            composable("search") {
                SearchScreen()
            }
            composable("saved") {
                SavedScreen()
            }
        }
    }


}


@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}