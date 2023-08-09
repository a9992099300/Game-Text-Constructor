package com.a9992099300.gameTextConstructor.logic.constructor.createBook

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.repository.book.BooksRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.BookModel
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CreateBookConstructorComponentImpl(
    private val componentContext: ComponentContext,
    override val onBack: () -> Unit,
    private val onBookEdit: () -> Unit,
    private val booksRepository: BooksRepository
) : ComponentContext by componentContext, CreateBookConstructorComponent {

    override val stateUi: StateFlow<StateUi<Unit>>
        get() = createBookViewModel.stateUi.asStateFlow()
    override val stateCategory: StateFlow<List<CategoryUiModel>>
        get() = createBookViewModel.stateCategory.asStateFlow()
    override val titleBook: StateFlow<String>
        get() = createBookViewModel.titleBook.asStateFlow()
    override val descriptionBook: StateFlow<String>
        get() = createBookViewModel.descriptionBook.asStateFlow()

    override fun chooseCategory(type: TypeCategory) {
        createBookViewModel.chooseCategory(type)
    }

    override fun changeTitle(title: String) {
        title.allowChangeValue<Unit>(
            64,
            allowSetValue = {
                createBookViewModel.titleBook.value = title
            },
            errorSetValue = { error ->
                createBookViewModel.stateUi.value = error
            }
        )
    }

    override fun changeDescription(description: String) {
        description.allowChangeValue<Unit>(
            1000,
            allowSetValue = {
                createBookViewModel.descriptionBook.value = description
            },
            errorSetValue = { error ->
                createBookViewModel.stateUi.value = error
            }
        )
    }

    override fun addBook() {
        createBookViewModel.addBook()
    }

    override fun onBackClicked() {
        onBack()
    }

    private val createBookViewModel =
        instanceKeeper.getOrCreate { CreateBooksViewModel(Dispatchers.Default) }

   private inner class CreateBooksViewModel(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

        val stateCategory: MutableStateFlow<List<CategoryUiModel>> =
            MutableStateFlow(Category.listDefaultCategory)

        val titleBook = MutableStateFlow("")

        val descriptionBook = MutableStateFlow("")

        fun chooseCategory(type: TypeCategory) {
            scope.launch {
                stateCategory.value = Category.listDefaultCategory.map {
                    val model = if (it.typeCategory == type) {
                        it.copy(selected = true)
                    } else {
                        it
                    }
                    model
                }
            }
        }

        fun addBook() {
            scope.launch {
                stateUi.value = StateUi.Loading
                val result = booksRepository.addBook(
                    BookModel(
                        title = titleBook.value,
                        description = descriptionBook.value,
                        category = stateCategory.value.find {
                            it.selected
                        }?.title ?: MainRes.string.other
                    )
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onBookEdit()
                        }
                    }

                    is Result.Error -> {
                        stateUi.value = StateUi.Error(result.error?.message ?: "")
                    }
                }
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}