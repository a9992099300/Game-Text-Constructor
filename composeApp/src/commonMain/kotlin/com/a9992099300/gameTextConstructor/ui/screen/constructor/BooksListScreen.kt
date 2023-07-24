package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonBookCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonPlusCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText

@Composable
fun ListBooksScreen(component: ListBookConstructorComponent) {

    val stateUi by component.stateUi.collectAsState(StateUi.Initial)
    val books by component.books.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp, 0.dp, 20.dp, 0.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                BooksListHeader(
                    component,
                    stateUi
                )

                BooksListContent(
                    books,
                    stateUi,
                    component
                )
            }

            when (stateUi) {
                is StateUi.Error -> {
                    CommonSnackBar(
                        message = (stateUi as StateUi.Error).messageError,
                        dismissSnack = {
                            component.closeSnack()
                        }
                    )
                }

                is StateUi.Loading -> {
                    CircularProgressIndicator(
                        color = Theme.colors.primaryBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun BooksListContent(
    books: List<BookDataModel>,
    stateUi: StateUi<Unit>,
    component: ListBookConstructorComponent
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            books
        ) { book ->
            CommonBookCard(
                modifier = Modifier.clickable {
                    component.editBook(book.bookId)
                },
                book = book,
                component,
            )
        }
        item {
            if (stateUi is StateUi.Success) {
                CommonPlusCard(
                    modifier = Modifier.clickable{
                        component.createNewBook()
                    },
                    component
                )
            }
        }
    }
}

@Composable
private fun BooksListHeader(
    component: ListBookConstructorComponent,
    stateUi: StateUi<Unit>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
    ) {

        HeaderText(
            text = MainRes.string.list_your_books,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier.size(24.dp).clickable {
                component.getBooksList()
            },
            imageVector = Icons.Default.Refresh, contentDescription = null,
            tint = Theme.colors.primaryAction,
        )
    }
}
