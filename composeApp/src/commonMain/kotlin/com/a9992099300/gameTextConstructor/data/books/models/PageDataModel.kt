package com.a9992099300.gameTextConstructor.data.books.models
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDataModel(
    @SerialName("pageId") val pageId: String,
    @SerialName("title") val title: String,
    @SerialName("arguments") val arguments: List<Argument> = listOf(),
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
                arguments = listOf(),
                description = "Описание страницы",
                addDescription = listOf(),
                imageUrl = "",
                deletable = false,
                listOf(
                    ItemPage(
                        action = listOf(
                            ActionPage.Move(
                                startDestination = "",
                                endDestination = "",
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
    @SerialName("block") val block: List<Argument> = listOf(),
    @SerialName("action") val action: List<ActionPage>
)

@Serializable
data class AddDescription(
    @SerialName("description") val description: String,
    @SerialName("argument") val argument: Argument,
)

@Serializable
sealed class ActionPage {
    @Serializable
    data class Move(
        @SerialName("startDestination") val startDestination: String,
        @SerialName("endDestination") val endDestination: String,
        @SerialName("arguments") val arguments: List<Argument> = listOf(),
        @SerialName("conditions") val conditions: List<Condition> = listOf(),
    ) : ActionPage()

    @Serializable
    data class EditInventory(
        @SerialName("subject") val subject: String,
        @SerialName("action") val action: Action,
        @SerialName("arguments") val arguments: List<Argument> = listOf(),
        @SerialName("conditions") val conditions: List<Condition> = listOf(),
    ) : ActionPage()

    @Serializable
    data class EditGlobalAchievement(
        @SerialName("achievement") val achievement: String,
        @SerialName("action") val action: Action,
        @SerialName("quantity") val quantity: Int,
        @SerialName("argument") val arguments: List<Argument> = listOf(),
        @SerialName("conditions") val conditions: List<Condition> = listOf(),
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
    @SerialName("quantity") val quantity: Int,
)

@Serializable
data class Argument(
    @SerialName("name") val name: String,
    @SerialName("quantity") val quantity: Int,
    @SerialName("howGetArgument")  val howGetArgument: HowGetArgument,
    @SerialName("lowQuantity") val lowQuantity: Int,
    @SerialName("highQuantity") val highQuantity: Int,
)

@Serializable
enum class ConditionMark(value: String) {
    LOW("low"),
    EQUALS("equals"),
    HIGH("high"),
    NOT_USE("not_use"),
}

@Serializable
enum class HowGetArgument(value: String) {
    EXACTLY("exactly"),
    RANDOM("random"),
}

