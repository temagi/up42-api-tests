package models

data class TaskResponse(
    val id: String,

    val displayId: String,

    val createdAt: String,

    val createdBy: Author,

    val updatedBy: Author,

    val updatedAt: String,

    val name: String,

    val parentsIds: List<String>,

    val blockName: String,

    val blockVersionTag: String?,
)
