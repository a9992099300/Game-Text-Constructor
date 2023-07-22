package com.a9992099300.gameTextConstructor.logic.constructor.editBook

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.repository.BooksRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
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
    private val onBack: () -> Unit,
    private val onBookEdit: () -> Unit,
) : ComponentContext by componentContext, EditBookConstructorComponent {

    private val booksRepository: BooksRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val stateCategory: MutableStateFlow<List<CategoryUiModel>> =
        MutableStateFlow(Category.listDefaultCategory)

    override val titleBook = MutableStateFlow("")
    override val descriptionBook = MutableStateFlow("")

    override val dataModel: MutableStateFlow<BookDataModel?> =
        MutableStateFlow(null)

    override fun chooseCategory(type: TypeCategory) {
        createBooksListRetainedInstance.chooseCategory(type)
    }

    override fun changeTitle(title: String) {
        title.allowChangeValue<Unit>(
            64,
            allowSetValue = {
                this.titleBook.value = title
            },
            errorSetValue = { error ->
                stateUi.value = error
            }
        )
    }

    override fun changeDescription(description: String) {
        description.allowChangeValue<Unit>(
            1000,
            allowSetValue = {
                this.descriptionBook.value = description
            },
            errorSetValue = { error ->
                stateUi.value = error
            }
        )
    }

    override fun editBook() {
        createBooksListRetainedInstance.editBook()
    }

    override fun deleteBook() {
        if (dataModel.value?.deletable == true) {
            createBooksListRetainedInstance.deleteBook()
        } else {
           stateUi.value = StateUi.Error(MainRes.string.prohibit_delete_book)
        }
    }

    override fun onBackClicked() {
        onBack()
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
                if (dataModel.value != null) {
                    val result = booksRepository.editBook(
                        dataModel.value!!.copy(
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

                        is Result.Error -> stateUi.value =
                            StateUi.Error(result.error?.message ?: "")
                    }
                }
            }
        }

        private fun getBook(bookId: String) {
            scope.launch {
                stateUi.value = StateUi.Loading
                when (val result = booksRepository.getBook(bookId)) {
                    is Result.Success -> {
                        chooseCategory(Category.listDefaultCategory
                            .find { it.title == result.value.category}?.typeCategory ?: TypeCategory.OTHER)
                        titleBook.value = result.value.title
                        descriptionBook.value = result.value.description
                        dataModel.value = result.value
                        stateUi.value = StateUi.Success(Unit)
                    }

                    is Result.Error -> stateUi.value = StateUi.Error(result.error?.message ?: "")
                }
            }
        }

        fun deleteBook(){
            scope.launch {
               val result =  dataModel.value?.bookId?.let { booksRepository.deleteBook(it) }
                when (result) {
                    is Result.Success-> {
                        withContext(Dispatchers.Main) {
                            onBookEdit()
                        }
                    }
                    is Result.Error -> stateUi.value = StateUi.Error(result.error?.message ?: "")
                    else -> {}
                }
            }
        }

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }
}