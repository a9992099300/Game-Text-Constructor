package com.a9992099300.gameTextConstructor.logic.constructor.menu.models

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemModel {
    abstract val title: String
    abstract val image: ImageVector

    data class Profile (
        override val title: String,
        override val image: ImageVector,
    ): ItemModel()

    data class ListBooks (
        override val title: String,
        override val image: ImageVector,
    ): ItemModel()

    data class Exit (
        override val title: String,
        override val image: ImageVector,
    ): ItemModel()
}
