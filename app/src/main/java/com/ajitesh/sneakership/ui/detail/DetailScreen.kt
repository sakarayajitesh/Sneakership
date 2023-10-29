package com.ajitesh.sneakership.ui.detail

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.Divider
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.sneakership.asPrice
import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.ui.theme.LightBlack
import com.ajitesh.sneakership.ui.theme.LightWhite
import com.ajitesh.sneakership.ui.theme.Red
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DetailSneaker(sneaker: Sneaker, addToCart: (String, () -> Unit) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
                .background(Color.LightGray)
        ) {
            GlideImage(
                model = sneaker.image,
                contentDescription = "Sneaker Image",
                modifier = Modifier
                    .fillMaxSize(fraction = 0.75f)
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(text = sneaker.brand, fontSize = 16.sp, color = Color.Gray)
                Text(text = sneaker.title, fontSize = 24.sp)
                Box(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val discount = sneaker.price - 20
                    Text(
                        text = discount.asPrice(),
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = sneaker.price.asPrice(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Red
                    )
                }
                Box(modifier = Modifier.height(24.dp))
                Divider(color = Color(0XFFCBD7E1))
                Box(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        addToCart(sneaker.id) {
                            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightBlack),
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                ) {
                    Text(text = "Add to Cart", color = LightWhite)
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
        },
        backgroundColor = Color.LightGray
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