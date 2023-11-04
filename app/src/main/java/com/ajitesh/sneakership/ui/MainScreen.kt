package com.ajitesh.sneakership.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajitesh.sneakership.R
import com.ajitesh.sneakership.ui.home.HomeScreen
import com.ajitesh.sneakership.ui.saved.SavedScreen
import com.ajitesh.sneakership.ui.search.SearchScreen
import com.ajitesh.sneakership.ui.theme.Black

sealed class BottomNavigationObject(val title: String, val icon: Int) {
    object Home : BottomNavigationObject(title = "home", icon = R.drawable.home_filled)
    object Search : BottomNavigationObject(title = "search", icon = R.drawable.search_filled)
    object Saved : BottomNavigationObject(title = "saved", icon = R.drawable.star_filled)
}

val bottomNavigationItems =
    listOf(BottomNavigationObject.Home, BottomNavigationObject.Search, BottomNavigationObject.Saved)


@Composable
fun MainScreen(navigate: (String) -> Unit) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SNEAKERSHIP", fontWeight = FontWeight.Medium) },
                actions = {
                    IconButton(
                        onClick = {
                            navigate("cart")
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                bottomNavigationItems.onEachIndexed { index, s ->
                    BottomNavigationItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(s.title)
                        },
                        selectedContentColor = Black,
                        unselectedContentColor = Color.Gray,
                        icon = {
                            Icon(
                                painter = painterResource(s.icon),
                                contentDescription = s.title,
//                                modifier = Modifier.size(24.dp)
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
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable("home") {
                HomeScreen(navigate = navigate)
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
    MainScreen(navigate = {})
}