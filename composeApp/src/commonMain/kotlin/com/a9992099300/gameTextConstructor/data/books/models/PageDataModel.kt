package com.a9992099300.gameTextConstructor.data.books.models

import com.a9992099300.gameTextConstructor.ui.screen.models.ActionTypeUI
import com.a9992099300.gameTextConstructor.ui.screen.models.ItemPageUi
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDataModel(
    @SerialName("bookId") val bookId: String,
    @SerialName("chapterId") val chapterId: String,
    @SerialName("sceneId") val sceneId: String,
    @SerialName("pageId") val pageId: String,
    @SerialName("title") val title: String,
    @SerialName("inputArguments") val inputArguments: List<Arg> = listOf(),
    //  @SerialName("outputArguments") val outputArguments: List<Arg> = listOf(),
    @SerialName("description") val description: String,
    @SerialName("addDescription") val addDescription: List<AddDescription> = listOf(),
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("deletable") val deletable: Boolean,
    @SerialName("items") val items: ItemPage = ItemPage(),
) {
    fun mapToUI() = PageUIModel(
        this.bookId,
        this.chapterId,
        this.sceneId,
        this.pageId,
        this.title,
        this.description,
        this.imageUrl,
        this.items.mapToUi(),
        this.deletable,
        false
    )
}

@Serializable
data class ItemPage(
    @SerialName("id") val actionId: String = "",
    @SerialName("description") val description: String = "Описание действия",
    @SerialName("block") val block: List<Condition> = listOf(),
    @SerialName("outputArguments") val outputArguments: List<Arg> = listOf(),
    @SerialName("action") val action: ActionPage = ActionPage.Move(
        "", ""
    )
) {
    fun mapToUi() = ItemPageUi(
        actionId,
        description,
        action.mapToUi()
    )
}

@Serializable
data class AddDescription(
    @SerialName("description") val description: String,
    @SerialName("argument") val argument: Arg,
)

@Serializable
sealed class ActionPage {

    abstract fun mapToUi(): ActionTypeUI

    @Serializable
    data class Move(
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestination") val endDestination: String,
    ) : ActionPage() {
        override fun mapToUi(): ActionTypeUI =
            ActionTypeUI.Move(
                startDestination = startDestination,
                endDestination = endDestination,
                selected = true
            )
    }

    @Serializable
    data class RandomMove(
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestinations") val endDestinations: List<String>,
    ) : ActionPage() {
        override fun mapToUi(): ActionTypeUI =
            ActionTypeUI.RandomMove(
                startDestination = startDestination,
                endDestinations = endDestinations,
                selected = true
            )
    }

    @Serializable
    data class ConditionMove(
        @SerialName("conditionMark") val conditionMark: ConditionMark,
        @SerialName("name") val nameThing: String,
        @SerialName("quantity") val quantity: Int,
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestinationIf") val endDestinationIf: String,
        @SerialName("endDestinationElse") val endDestinationElse: String,
    ) : ActionPage() {
        override fun mapToUi(): ActionTypeUI =
            ActionTypeUI.ConditionMove(
                conditionMark = conditionMark,
                nameThing = nameThing,
                quantity = quantity,
                startDestination = startDestination,
                endDestinationIf = endDestinationIf,
                endDestinationElse = endDestinationElse,
                selected = true
            )
    }

//    @Serializable
//    data class EditInventory(
//        @SerialName("subject") val argument: Arg,
//        @SerialName("action") val action: Action,
//       // @SerialName("quantity") val quantity: Int,
//      //  @SerialName("conditions") val conditions: List<Condition> = listOf(),
//    ) : ActionPage()
//
//    @Serializable
//    data class EditAchievement(
//        @SerialName("achievement") val argument: Arg,
//        @SerialName("action") val action: Action,
//     //   @SerialName("quantity") val quantity: Int,
//     //   @SerialName("conditions") val conditions: List<Condition> = listOf(),
//    ) : ActionPage()


}

@Serializable
enum class Action(action: String) {
    INCREASE("increase"),
    REDUCE("reduce"),
}

@Serializable
data class Condition(
    @SerialName("conditionMark") val conditionMark: ConditionMark,
    @SerialName("name") val name: String,
    @SerialName("lowValue") val lowValue: Int,
    @SerialName("heightValue") val heightValue: Int,
)


//interface arg
//
//@Serializable
//data class Argument(
//    @SerialName("name") val name: String,
//    @SerialName("quantity") val quantity: Int,
//    @SerialName("howGetArgument")  val howGetArgument: HowGetArgument,
//    @SerialName("lowQuantity") val lowQuantity: Int,
//    @SerialName("highQuantity") val highQuantity: Int,
//) : arg


sealed interface Arg {
    @Serializable
    data class ArgumentExactly(
        @SerialName("name") val name: String,
        @SerialName("quantity") val quantity: Int,
    ) : Arg

    @Serializable
    data class ArgumentRandom(
        @SerialName("name") val name: String,
        @SerialName("lowQuantity") val lowQuantity: Int,
        @SerialName("highQuantity") val highQuantity: Int,
    ) : Arg
}


@Serializable
enum class ConditionMark(value: String) {
    LOW("low"),
    EQUALS("equals"),
    HIGH("high"),
    BETWEEN("between"),
    NOT_USE("not_use"),
}

@Serializable
enum class HowGetArgument(value: String) {
    EXACTLY("exactly"),
    RANDOM("random"),
}

