package com.a9992099300.gameTextConstructor.utils

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel

object Category{
    val listDefaultCategory = listOf(
        CategoryUiModel(
            typeCategory = TypeCategory.ROMAN,
            title = MainRes.string.roman,
            selected = false
        ),
        CategoryUiModel(
            typeCategory = TypeCategory.HORROR,
            title = MainRes.string.horror,
            selected = false
        ),
        CategoryUiModel(
            typeCategory = TypeCategory.ADVENTURE,
            title = MainRes.string.adventure,
            selected = false
        ),
        CategoryUiModel(
            typeCategory = TypeCategory.OTHER,
            title = MainRes.string.other,
            selected = false
        )
    )
}

enum class TypeCategory {
    ROMAN,
    HORROR,
    ADVENTURE,
    OTHER
}