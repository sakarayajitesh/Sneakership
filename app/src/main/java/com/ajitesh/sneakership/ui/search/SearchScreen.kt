package com.ajitesh.sneakership.ui.search

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Search") })
        }
    ) { _ ->

    }
}