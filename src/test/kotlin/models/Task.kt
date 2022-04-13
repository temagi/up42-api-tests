package models

data class Task(
    val name: String,

    val parentName: String? = null,

    val blockId: String
)
