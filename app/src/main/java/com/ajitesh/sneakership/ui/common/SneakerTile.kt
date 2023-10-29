package com.ajitesh.sneakership.ui.common

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.sneakership.R
import com.ajitesh.sneakership.asPrice
import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.ui.theme.Red
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.util.UUID

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SneakerTile(
    modifier: Modifier = Modifier,
    sneaker: Sneaker,
    addToCart: (String, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    Box(modifier = modifier.border(color = Color.Gray, width = 0.5.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
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
                        .padding(24.dp)
                        .align(Alignment.Center)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Brand", color = Color.Gray, fontSize = 12.sp)
                Text(text = sneaker.title, color = Color.Black)
                Box(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val discount = sneaker.price - 20
                    Text(
                        text = discount.asPrice(),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = sneaker.price.asPrice(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Red
                    )
                }
            }

        }
        IconButton(
            onClick = {
                addToCart(sneaker.id) {
                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.star_outlined),
                contentDescription = "Add to cart",
            )
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
    SneakerTile(sneaker = sneaker, addToCart = { _, _ -> })
}