package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun CommonCard(
    modifier: Modifier,
    title: String = "",
    number: String = "",
    description: String = "",
    imageUrl: String = "",
    selected: Boolean = false,
    sceneHide: Boolean = false,
    onEdit: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        border = if (selected) {
            BorderStroke(2.dp, Theme.colors.primaryBackground)
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            PostCommonHeader(title, number, !sceneHide, onEdit)

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(!sceneHide) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                ) {
                    Text(
                        text = description,
                        color = Theme.colors.primaryTextColor,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun PostCommonHeader(
    title: String,
    number: String,
    sceneHide: Boolean,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 0.dp),
    ) {

        Row {
            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(sceneHide) {
                Text(
                    text = number,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Theme.colors.primaryAction,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onEdit()
                        },
                    imageVector = Icons.Default.Edit,
                    contentDescription = Icons.Default.Settings.name,
                    tint = Theme.colors.primaryAction,
                )
            }
        }

        Column {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

