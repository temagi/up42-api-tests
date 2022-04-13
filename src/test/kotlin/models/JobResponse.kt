package models

data class JobResponse(
    val id: String,

    val displayId: String,

    val createdAt: String,

    val createdBy: Author,

    val updatedBy: Author,

    val updatedAt: String,

    // TODO: To enum
    val status: String,

    val name: String?,

    val startedAt: String?,

    val finishedAt: String?,

    val inputs: Job,

    val mode: String,

    val workflowId: String,

    val workflowName: String
)
