package com.a9992099300.gameTextConstructor.data.books.models
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
    @SerialName("inputArguments") val inputArguments: List<InventoryArgDataModel> = listOf(),
    @SerialName("outputArguments") val outputArguments: List<InventoryArgDataModel> = listOf(),
    @SerialName("description") val description: String,
    @SerialName("addDescription") val addExtraDescription: List<AddDescription> = listOf(),
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("deletable") val deletable: Boolean,
    @SerialName("items") val items: List<ItemPage>,
) {
    fun mapToUI() = PageUIModel(
        this.pageId,
        this.title,
        this.description,
        this.imageUrl,
        this.deletable,
        false
    )

//    companion object {
//        fun createEmptyPage(sceneId: String, pageNumber: Int = 1) =
//            PageDataModel(
//                pageId = "${sceneId}_$pageNumber",
//                title = "Страница $pageNumber",
//                inputArguments = listOf(),
//                outputArguments = listOf(),
//                description = "Описание страницы",
//                addExtraDescription = listOf(),
//                imageUrl = "",
//                deletable = false,
//                listOf(
//                    ItemPage(
//                        action = listOf(
//                            ActionMovePage.Move(
//                                startDestination = "",
//                                endDestination = ""
//                            )
//                        ),
//                        block = listOf()
//                    )
//                )
//            )
//    }
}

@Serializable
data class ItemPage(
    @SerialName("id") val id: Int = 1,
    @SerialName("description") val description: String = "Описание действия",
    @SerialName("block") val block: List<Condition> = listOf(),
    @SerialName("outputArguments") val outputArguments: List<Arg> = listOf(),
    @SerialName("action") val action: List<ActionMovePage>
)

@Serializable
data class AddDescription(
    @SerialName("description") val description: String,
    @SerialName("argument") val argument: InventoryArgDataModel,
)

@Serializable
sealed class ActionMovePage {
    @Serializable
    data class Move(
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestination") val endDestination: String,
    ) : ActionMovePage()

    @Serializable
    data class RandomMove(
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestination") val endDestinations: List<String>,
    ) : ActionMovePage()

    @Serializable
    data class ConditionMove(
        @SerialName("conditionMoveDestination") val conditionMoveDestination: List<ConditionMoveDestination>
    ) : ActionMovePage()

//    @Serializable
//    data class EditInventory(
//        @SerialName("subject") val argument: Arg,
//        @SerialName("action") val action: Action,
//       // @SerialName("quantity") val quantity: Int,
//      //  @SerialName("conditions") val conditions: List<Condition> = listOf(),
//    ) : ActionMovePage()
//
//    @Serializable
//    data class EditAchievement(
//        @SerialName("achievement") val argument: Arg,
//        @SerialName("action") val action: Action,
//     //   @SerialName("quantity") val quantity: Int,
//     //   @SerialName("conditions") val conditions: List<Condition> = listOf(),
//    ) : ActionMovePage()

}

@Serializable
data class ConditionMoveDestination(
    @SerialName("conditionMark") val conditionMark: ConditionMark,
    @SerialName("name") val name: String,
    @SerialName("quantity1") val lowValue: Int,
    @SerialName("quantity2") val heightValue: Int,
    @SerialName("startDestination") val startDestination: String,
    @SerialName("endDestination") val endDestination: String,
)

@Serializable
enum class Action(action: String) {
    INCREASE("increase"),
    REDUCE("reduce"),
}

@Serializable
data class Condition(
    @SerialName("conditionMark") val conditionMark: ConditionMark,
    @SerialName("name") val inventoryDataModel: InventoryDataModel,
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

