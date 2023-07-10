package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponent

@Composable
fun ListBooksScreen(component: ListBookConstructorComponent) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp ,0.dp, 20.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("BOOKS")
        }
    }

}