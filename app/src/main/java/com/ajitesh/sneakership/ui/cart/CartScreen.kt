package com.ajitesh.sneakership.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.sneakership.TAXANDCHARGES
import com.ajitesh.sneakership.asPrice
import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.getTotalPrice
import com.ajitesh.sneakership.ui.common.SneakerTileImage
import com.ajitesh.sneakership.ui.theme.LightOrange
import java.util.UUID

@Composable
fun CartScreen(
    uiState: CartUiState,
    deleteFromCart: (String) -> Unit,
    onCheckout: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(topBar = { CartAppBar(navigateBack) }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            if (uiState is CartUiState.ShowCartList) {
                val sneakerList = uiState.cartList
                if (sneakerList.isEmpty()) {
                    CartEmpty(modifier = Modifier.align(Alignment.Center))
                } else {
                    CartItems(
                        sneakerList = sneakerList,
                        deleteFromCart = deleteFromCart,
                        onCheckout = onCheckout
                    )
                }
            }
        }
    }
}

@Composable
private fun CartEmpty(modifier: Modifier = Modifier) {
    Text(
        text = "Cart is Empty",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}

@Composable
private fun CartItems(
    sneakerList: List<Sneaker>,
    deleteFromCart: (String) -> Unit,
    onCheckout: () -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        repeat(sneakerList.size) { index ->
            val it = sneakerList[index]
            Box {
                CartTile(sneaker = it)
                IconButton(
                    modifier = Modifier
                        .background(color = LightOrange, shape = CircleShape)
                        .size(28.dp)
                        .align(
                            Alignment.TopEnd
                        ),
                    onClick = { deleteFromCart(it.id) }
                ) {
                    Icon(
                        Icons.Filled.Close,
                        tint = Color.White,
                        contentDescription = "Add to cart",
                    )
                }
            }
        }
        Box(modifier = Modifier.height(16.dp))
        OrderDetails(sneakerList = sneakerList, onCheckout = onCheckout)
        Box(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun CartTile(sneaker: Sneaker) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SneakerTileImage(modifier = Modifier.width(120.dp), image = sneaker.image)
            Box(modifier = Modifier.width(24.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(sneaker.title, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(sneaker.price.asPrice(), color = Color.Gray)
            }
        }
    }
}

@Composable
private fun OrderDetails(
    sneakerList: List<Sneaker>,
    onCheckout: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    val totalPrice = sneakerList.getTotalPrice() + TAXANDCHARGES
    Column(modifier = modifier.padding(horizontal = 8.dp)) {
        Text(
            text = "Order Details",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = Color.Black
        )
        Box(modifier = modifier.height(8.dp))
        Text(
            text = "Subtotal : ${sneakerList.getTotalPrice().asPrice()}",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Box(modifier = modifier.height(4.dp))
        Text(
            text = "Taxes and Charges : ${TAXANDCHARGES.asPrice()}",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Box(modifier = modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(text = "Total : ", color = Color.Gray)
                Text(text = totalPrice.asPrice(), fontWeight = FontWeight.Medium)
            }
            Button(
                onClick = onCheckout,
                modifier = Modifier.height(60.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightOrange)
            ) {
                Text(text = "Check Out", color = Color.White, fontSize = 16.sp)
            }
        }

    }
}

@Composable
private fun CartAppBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Cart", fontWeight = FontWeight.Medium) },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewCartScreen() {
    val sneaker = Sneaker(
        id = UUID.randomUUID().toString(),
        price = 200,
        title = "Puma RS-X",
        image = FakeData.image,
        brand = "Puma",
        yearOfRelease = "2022"
    )
    val uiState = CartUiState.ShowCartList(listOf(sneaker))
    CartScreen(uiState = uiState, deleteFromCart = {}, onCheckout = {}, navigateBack = {})
}