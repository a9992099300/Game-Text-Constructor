package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel
import com.a9992099300.gameTextConstructor.utils.TypeCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonFilterChip(categoryUiModel: CategoryUiModel, onClick: (TypeCategory) -> Unit) {
    FilterChip(
        modifier = Modifier.padding(4.dp),
        selected = categoryUiModel.selected,
        onClick = { onClick.invoke(categoryUiModel.typeCategory)
                  },
        label = { Text(categoryUiModel.title) },
        colors = FilterChipDefaults.filterChipColors(
            selectedLabelColor = Theme.colors.secondaryTextColor,
            selectedContainerColor = Theme.colors.primaryAction
        ),
        leadingIcon = if (categoryUiModel.selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    tint = Theme.colors.secondaryTextColor
                )
            }
        } else {
            null
        }
    )
}