package com.ajitesh.sneakership.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ajitesh.sneakership.R
import com.ajitesh.sneakership.ui.theme.LightBlack
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(navigate: (String) -> Unit) {
    val background = Color.LightGray
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Box(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .background(background)
                    .weight(1f)
                    .clickable {
                        navigate("catalog")
                    }
            ) {
                GlideImage(
                    model = R.drawable.men_shoe,
                    contentDescription = "Men shoe",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                HomeTileTitle(
                    text = "For Him",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            Box(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .background(background)
                    .weight(1f)
                    .clickable {
                        navigate("catalog")
                    }
            ) {
                GlideImage(
                    model = R.drawable.ladies_shoe,
                    contentDescription = "Ladies shoe",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                HomeTileTitle(
                    text = "For Her", modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
        Box(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(background)
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    navigate("catalog")
                }
        ) {
            GlideImage(
                model = R.drawable.sport_shoe,
                contentDescription = "Sports shoe",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            HomeTileTitle(
                text = "Sports", modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
        Box(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeTileTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .border(width = 1.dp, color = LightBlack)
            .background(Color.White.copy(alpha = 0.5f))
            .padding(16.dp)

    )
}
