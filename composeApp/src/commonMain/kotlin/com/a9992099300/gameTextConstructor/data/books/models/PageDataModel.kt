package com.a9992099300.gameTextConstructor.data.books.models
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDataModel(
    @SerialName("pageId") val pageId: String,
    @SerialName("title") val title: String,
    @SerialName("inputArguments") val inputArguments: List<Arg> = listOf(),
  //  @SerialName("outputArguments") val outputArguments: List<Arg> = listOf(),
    @SerialName("description") val description: String,
    @SerialName("addDescription") val addDescription: List<AddDescription> = listOf(),
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

    companion object {
        fun createEmptyPage(sceneId: String, pageNumber: Int = 1) =
            PageDataModel(
                pageId = "${sceneId}_$pageNumber",
                title = "Страница $pageNumber",
                inputArguments = listOf(),
            //    outputArguments = listOf(),
                description = "Описание страницы",
                addDescription = listOf(),
                imageUrl = "",
                deletable = false,
                listOf(
                    ItemPage(
                        action = listOf(
                            ActionPage.Move(
                                startDestination = "",
                                endDestination = ""
                            )
                        ),
                        block = listOf()
                    )
                )
            )
    }
}

@Serializable
data class ItemPage(
    @SerialName("id") val id: Int = 1,
    @SerialName("description") val description: String = "Описание действия",
    @SerialName("block") val block: List<Condition> = listOf(),
    @SerialName("outputArguments") val outputArguments: List<Arg> = listOf(),
    @SerialName("action") val action: List<ActionPage>
)

@Serializable
data class AddDescription(
    @SerialName("description") val description: String,
    @SerialName("argument") val argument: Arg,
)

@Serializable
sealed class ActionPage {
    @Serializable
    data class Move(
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestination") val endDestination: String,
    ) : ActionPage()

    @Serializable
    data class ConditionMove(
        @SerialName("conditionMark") val conditionMark: ConditionMark,
        @SerialName("name") val name: String,
        @SerialName("quantity") val quantity: Int,
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestination") val endDestination: String,
    ) : ActionPage()

    @Serializable
    data class EditInventory(
        @SerialName("subject") val argument: Arg,
        @SerialName("action") val action: Action,
       // @SerialName("quantity") val quantity: Int,
      //  @SerialName("conditions") val conditions: List<Condition> = listOf(),
    ) : ActionPage()

    @Serializable
    data class EditAchievement(
        @SerialName("achievement") val argument: Arg,
        @SerialName("action") val action: Action,
     //   @SerialName("quantity") val quantity: Int,
     //   @SerialName("conditions") val conditions: List<Condition> = listOf(),
    ) : ActionPage()

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

