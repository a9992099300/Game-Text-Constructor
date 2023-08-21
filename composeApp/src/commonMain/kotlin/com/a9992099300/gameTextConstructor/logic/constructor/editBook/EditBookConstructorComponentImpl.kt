package com.a9992099300.gameTextConstructor.logic.constructor.editBook

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.repository.book.BooksRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_DESCRIPTION
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_TITLE
import com.a9992099300.gameTextConstructor.ui.screen.models.BookUiModel
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel
import com.a9992099300.gameTextConstructor.utils.Category
import com.a9992099300.gameTextConstructor.utils.TypeCategory
import com.a9992099300.gameTextConstructor.utils.allowChangeValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class EditBookConstructorComponentImpl(
    private val componentContext: ComponentContext,
    private val bookId: String,
    private val onBookEdit: () -> Unit,
    private val onEditScenes: (book: BookUiModel) -> Unit,
    override val onBack: () -> Unit,
) : ComponentContext by componentContext, EditBookConstructorComponent {

    private val booksRepository: BooksRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val stateCategory: MutableStateFlow<List<CategoryUiModel>> =
        MutableStateFlow(Category.listDefaultCategory)

    override val uiModel: MutableStateFlow<BookUiModel> =
        MutableStateFlow(BookUiModel())

    override fun chooseCategory(type: TypeCategory) {
        createBooksListRetainedInstance.chooseCategory(type)
    }

    override fun changeTitle(title: String) {
        title.allowChangeValue<Unit>(
            64,
            allowSetValue = {
                this.uiModel.value = uiModel.value.copy(title = title)
            },
            errorSetValue = { error ->
                stateUi.value = (error as StateUi.Error).copy(codeError = ERROR_TITLE)
            }
        )
    }

    override fun changeDescription(description: String) {
        description.allowChangeValue<Unit>(
            1000,
            allowSetValue = {
                this.uiModel.value = uiModel.value.copy(description = description)
            },
            errorSetValue = { error ->
                stateUi.value = (error as StateUi.Error).copy(codeError = ERROR_DESCRIPTION)
            }
        )
    }

    override fun editBook() {
        createBooksListRetainedInstance.editBook()
    }

    override fun onEditScenes() {
        onEditScenes.invoke(uiModel.value)
    }

    override fun deleteBook() {
        if (uiModel.value.deletable) {
            createBooksListRetainedInstance.deleteBook()
        } else {
            stateUi.value = StateUi.Error(MainRes.string.prohibit_delete_book)
        }
    }


    private val createBooksListRetainedInstance =
        instanceKeeper.getOrCreate { CreateBooksListRetainedInstance(Dispatchers.Default) }

    inner class CreateBooksListRetainedInstance(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        init {
            getBook(bookId)
        }

        fun chooseCategory(type: TypeCategory) {
            scope.launch {
                stateCategory.value = Category.listDefaultCategory.map {
                    val model = if (it.typeCategory == type) {
                        uiModel.value = uiModel.value.copy(category = it.title)
                        it.copy(selected = true)
                    } else {
                        it
                    }
                    model
                }
            }
        }

        fun editBook() {
            scope.launch {
                stateUi.value = StateUi.Loading
                val result = booksRepository.editBook(
                    uiModel.value.mapToData()
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onBookEdit()
                        }
                    }

                    is Result.Error -> stateUi.value =
                        StateUi.Error(result.error?.message ?: "")
                }
            }
        }

        private fun getBook(bookId: String) {
            scope.launch {
                stateUi.value = StateUi.Loading
                when (val result = booksRepository.getBook(bookId)) {
                    is Result.Success -> {
                        chooseCategory(Category.listDefaultCategory
                            .find { it.title == result.value.category }?.typeCategory
                            ?: TypeCategory.OTHER
                        )

                        uiModel.value = result.value.mapToUI()
                        stateUi.value = StateUi.Success(Unit)
                    }

                    is Result.Error -> stateUi.value = StateUi.Error(result.error?.message ?: "")
                }
            }
        }

        fun deleteBook() {
            scope.launch {
                when (val result = booksRepository.deleteBook(uiModel.value.bookId)) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onBookEdit()
                        }
                    }

                    is Result.Error -> stateUi.value = StateUi.Error(result.error?.message ?: "")
                }
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}