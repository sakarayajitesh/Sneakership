package com.ajitesh.sneakership.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.ui.common.SneakerTile
import com.ajitesh.sneakership.ui.theme.Black
import java.util.UUID

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    searchText: String,
    searchTextChange: (String) -> Unit,
    navigate: (String) -> Unit,
    addToCart: (String, () -> Unit) -> Unit
) {
    Scaffold(
        topBar = { HomeAppBar(navigate = navigate) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Black, modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                is HomeUiState.ShowSneakerList -> {
                    val sneakerList = uiState.sneakers
                    if (sneakerList.isEmpty() && searchText.isEmpty()) {
                        Text(
                            text = "No Sneakers Available at this moment",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        SneakerList(
                            sneakerList = sneakerList,
                            searchText = searchText,
                            searchTextChange = searchTextChange,
                            addToCart = addToCart,
                            navigate = navigate
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SneakerList(
    sneakerList: List<Sneaker>,
    searchText: String,
    searchTextChange: (String) -> Unit,
    addToCart: (String, () -> Unit) -> Unit,
    navigate: (String) -> Unit
) {
    Column {
        Box(modifier = Modifier.height(16.dp))
        HomeSearchBar(searchText = searchText, searchTextChange = searchTextChange)
        Box(modifier = Modifier.height(16.dp))
        if (searchText.isNotEmpty() && sneakerList.isEmpty()) {
            Text(text = "No results found")
        } else
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                content = {
                    items(sneakerList) {
                        SneakerTile(
                            sneaker = it,
                            addToCart = addToCart,
                            modifier = Modifier.clickable {
                                navigate("detail/${it.id}")
                            }
                        )
                    }
                })
    }
}

@Composable
private fun HomeSearchBar(searchText: String, searchTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = searchTextChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { searchTextChange("") }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear")
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(color = Color.Gray),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Black,
        )
    )
}

@Composable
private fun HomeAppBar(navigate: (String) -> Unit) {
    TopAppBar(
        title = { Text(text = "SNEAKERSHIP", fontWeight = FontWeight.Medium) },
        elevation = 0.dp,
        actions = {
            IconButton(onClick = {
                navigate("cart")
            }) {
                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Cart")
            }
        }
    )
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    val sneaker = Sneaker(
        id = UUID.randomUUID().toString(),
        price = 200,
        title = "Puma RS-X",
        image = FakeData.image,
        brand = "Puma",
        yearOfRelease = "2022"
    )
    val uiState = HomeUiState.ShowSneakerList(listOf(sneaker, sneaker, sneaker, sneaker))
    HomeScreen(
        uiState = uiState,
        searchText = "",
        searchTextChange = {},
        addToCart = { _, _ -> },
        navigate = {})
}