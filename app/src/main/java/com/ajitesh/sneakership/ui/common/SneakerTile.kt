package com.ajitesh.sneakership.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.sneakership.asPrice
import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.ui.theme.LightOrange
import java.util.UUID

@Composable
fun SneakerTile(modifier: Modifier = Modifier, sneaker: Sneaker, addToCart: (String) -> Unit) {
    Card(modifier = modifier.padding(4.dp), shape = RoundedCornerShape(16.dp)) {
        Box(modifier = Modifier.padding(12.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.height(16.dp))
                SneakerTileImage(sneaker.image)
                Box(modifier = modifier.height(4.dp))
                Text(text = sneaker.title, fontWeight = FontWeight.Medium, color = Color.Black)
                Text(text = sneaker.price.asPrice(), fontWeight = FontWeight.Medium, fontSize = 20.sp)
            }
            IconButton(
                modifier = Modifier
                    .background(color = LightOrange, shape = CircleShape)
                    .size(28.dp),
                onClick = {
                    addToCart(sneaker.id)
                }
            ) {
                Icon(
                    Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = "Add to cart",
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewSneakerTile() {
    val sneaker = Sneaker(
        id = UUID.randomUUID().toString(),
        price = 200,
        title = "Puma RS-X",
        image = FakeData.image,
        brand = "Puma",
        yearOfRelease = "2022"
    )
    SneakerTile(sneaker = sneaker, addToCart = {})
}