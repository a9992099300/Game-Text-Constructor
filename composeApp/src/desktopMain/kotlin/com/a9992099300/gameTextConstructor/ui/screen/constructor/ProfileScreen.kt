package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.logic.constructor.child.ProfileConstructorComponent

@Composable
fun ProfileScreen(component: ProfileConstructorComponent) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp ,0.dp, 20.dp, 0.dp)
        ) {
            Text("PROFILE")
        }

}