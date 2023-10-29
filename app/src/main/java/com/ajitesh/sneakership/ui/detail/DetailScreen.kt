package com.ajitesh.sneakership.ui.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.sneakership.asPrice
import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.ui.common.SneakerTileImage
import com.ajitesh.sneakership.ui.theme.LightOrange
import java.util.UUID

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    addToCart: (String, () -> Unit) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = { DetailAppBar(navigateBack) }
    ) { _ ->
        Box {
            when (uiState) {
                is DetailUiState.Loading -> DetailLoading()
                is DetailUiState.ShowSneaker -> {
                    val sneaker = uiState.sneaker
                    DetailSneaker(sneaker = sneaker, addToCart = addToCart)
                }
            }
        }

    }
}

@Composable
private fun DetailSneaker(sneaker: Sneaker, addToCart: (String, () -> Unit) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.18f)
//                .background(Color.Gray)
                .padding(horizontal = 36.dp)
        ) {
            SneakerTileImage(image = sneaker.image)
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(text = sneaker.title, fontSize = 24.sp, fontWeight = FontWeight.Medium)
                Box(modifier = Modifier.height(24.dp))
                Text(text = "Brand : ${sneaker.brand}", fontSize = 20.sp, color = Color.Gray)
                Box(modifier = Modifier.height(24.dp))
                Text(
                    text = "Year of release : ${sneaker.yearOfRelease}",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Box(modifier = Modifier.height(24.dp))
                Row {
                    Text(text = "Price: ", fontSize = 20.sp, color = Color.Gray)
                    Text(
                        text = sneaker.price.asPrice(),
                        fontSize = 20.sp,
                        color = LightOrange,
                        fontWeight = FontWeight.Medium
                    )
                }
                Box(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = {
                            addToCart(sneaker.id) {
                                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(50.dp),
                    ) {
                        Text(text = "Add to Cart", color = LightOrange)
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "Buy now", Toast.LENGTH_SHORT)
                                .show()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = LightOrange),
                        modifier = Modifier.height(50.dp),
                    ) {
                        Text(text = "Buy Now", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailLoading() {
    CircularProgressIndicator()
}

@Composable
private fun DetailAppBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {},
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDetailScreen() {
    val sneaker = Sneaker(
        id = UUID.randomUUID().toString(),
        price = 200,
        title = "Puma RS-X",
        image = FakeData.image,
        brand = "Puma",
        yearOfRelease = "2022"
    )
    val uiState = DetailUiState.ShowSneaker(sneaker)
    DetailScreen(uiState = uiState, addToCart = { _, _ -> }, navigateBack = {})
}