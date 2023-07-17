package com.a9992099300.gameTextConstructor.logic.constructor.listBooks

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.repository.BooksRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListBookConstructorComponentImpl(
    componentContext: ComponentContext,
): ComponentContext by componentContext, ListBookConstructorComponent {

    private val booksListRepository: BooksRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)

    override val books: MutableStateFlow<List<BookDataModel>> =
        MutableStateFlow(listOf())

    override fun getBooksList() {
        booksListRetainedInstance.getBooksList()
    }

    private val booksListRetainedInstance =
        instanceKeeper.getOrCreate { BooksListRetainedInstance(Dispatchers.Default) }

    inner class BooksListRetainedInstance(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes

        private val scope = CoroutineScope(mainContext + SupervisorJob())
        init {
            getBooksList()
        }

        fun getBooksList() {
            scope.launch {
                stateUi.value = StateUi.Loading
                when (val result = booksListRepository.getBooksList()) {
                    is Result.Success -> {
                        stateUi.value = StateUi.Success(Unit)
                        books.value = result.value
                    }
                    is Result.Error -> {
                        stateUi.value = StateUi.Error(result.error?.message ?: "Error")
                    }
                }
            }
        }

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }

}