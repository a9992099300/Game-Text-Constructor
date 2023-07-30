package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.logic.constructor.RootConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun MainConstructorScreen(component: RootConstructorComponent) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.primaryBackground),
        contentAlignment = Alignment.Center,
    ) {
          Column(
              modifier = Modifier
                  .wrapContentWidth()
                  .fillMaxHeight()
                  .padding(0.dp, 30.dp, 0.dp, 30.dp),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.Start
          ) {
              MenuConstructorContent(
                  component
              )
          }
    }
}