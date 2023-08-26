package com.a9992099300.gameTextConstructor.ui.preview

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.createPage.CreateOrEditPageComponent
import com.a9992099300.gameTextConstructor.ui.screen.constructor.book.CreatePageScreen
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


//@Preview
//@Composable
//fun PreviewPageScreen() {
//    CreatePageScreen(CreateOrEditPageComponentPrev())
//}
//
//class CreateOrEditPageComponentPrev() : CreateOrEditPageComponent {
//
//    override val uiPageModel: StateFlow<PageUIModel> = MutableStateFlow(PageUIModel())
//
//    override val uiActions: StateFlow<List<ItemPage>>  = MutableStateFlow(listOf())
//
//    override val onBack: () -> Unit = {
//
//    }
//    override val stateUi = MutableStateFlow(StateUi.Initial)
//
//}