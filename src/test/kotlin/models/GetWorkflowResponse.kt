package models

data class GetWorkflowResponse(
    val id: String,

    val name: String,

    val description: String,

    // TODO: Move to datetime format?
    val createdAt: String,

    val updatedAt: String,

    // TODO: is it enough of Int?
    val totalProcessingTime: Int,

    val createdBy: Author,

    val updatedBy: Author
)
