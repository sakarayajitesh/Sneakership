package com.ajitesh.sneakership.ui.saved

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun SavedScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Saved Items") })
        }
    ) { _ ->

    }
}