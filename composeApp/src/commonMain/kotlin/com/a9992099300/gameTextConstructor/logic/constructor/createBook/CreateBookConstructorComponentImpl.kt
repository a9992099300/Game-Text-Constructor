package com.a9992099300.gameTextConstructor.logic.constructor.createBook

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.repository.BooksRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.BookModel
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel
import com.a9992099300.gameTextConstructor.utils.Category
import com.a9992099300.gameTextConstructor.utils.TypeCategory
import com.a9992099300.gameTextConstructor.utils.sizeAllow
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

class CreateBookConstructorComponentImpl(
    private val componentContext: ComponentContext,
    private val onBack: () -> Unit,
    private val onBookEdit: () -> Unit,
) : ComponentContext by componentContext, CreateBookConstructorComponent {

    private val booksRepository: BooksRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val stateCategory: MutableStateFlow<List<CategoryUiModel>> =
        MutableStateFlow(Category.listDefaultCategory)

    override val titleBook = MutableStateFlow("")
    override val descriptionBook = MutableStateFlow("")

    override fun chooseCategory(type: TypeCategory) {
        createBooksListRetainedInstance.chooseCategory(type)
    }

    override fun changeTitle(title: String) {
        title.sizeAllow<Unit>(
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
    if (description.length < 1000) {
        this.descriptionBook.value = description
    } else {
        stateUi.value = StateUi.Error(MainRes.string.max_string.format("1000"))
    }
}

override fun editBook() {
    createBooksListRetainedInstance.editBook()
}

override fun onBackClicked() {
    onBack()
}

private val createBooksListRetainedInstance =
    instanceKeeper.getOrCreate { CreateBooksListRetainedInstance(Dispatchers.Default) }

inner class CreateBooksListRetainedInstance(mainContext: CoroutineContext) :
    InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())
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

                is Result.Error -> stateUi.value = StateUi.Error(result.error?.message ?: "")
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}
}