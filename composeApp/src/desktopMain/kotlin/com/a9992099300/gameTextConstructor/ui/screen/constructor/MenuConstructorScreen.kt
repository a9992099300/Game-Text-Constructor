package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.constructor.RootConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.menu.models.ItemModel
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import compose.icons.FeatherIcons
import compose.icons.feathericons.Book
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuConstructorScreen(
    component: RootConstructorComponent
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val items = listOf(
        ItemModel.Profile(
            MainRes.string.profile,
            Icons.Default.Face
        ),
        ItemModel.ListBooks(
            MainRes.string.list_books,
            FeatherIcons.Book
        ),
        ItemModel.Exit(
            MainRes.string.exit,
            Icons.Default.ExitToApp
        )
    )
    val selectedItem = remember { mutableStateOf(items[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.offset((30).dp, 0.dp),
                drawerShape = ShapeDefaults.Medium,
            ) {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.image, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = item
                            component.menuConstructorComponent.openItem(item)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                //    .padding(0.dp, 0.dp, 0.dp, 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Children(
                    stack = component.pageStack,
                    animation = stackAnimation(fade() + scale()),
                ) {
                    when (val child = it.instance) {
                        is RootConstructorComponent.Page.ListBooks -> {
                            ListBooksScreen(child.component)
                        }
                        is RootConstructorComponent.Page.Book -> {

                        }
                        is RootConstructorComponent.Page.Profile -> {
                            ProfileScreen(child.component)
                        }

                        is RootConstructorComponent.Page.CreateBook -> {
                            CreateBookScreen(child.component) }

                        is RootConstructorComponent.Page.EditBook -> {
                            EditBookScreen(child.component)
                        }
                    }
                }

            }
        }
    )
}