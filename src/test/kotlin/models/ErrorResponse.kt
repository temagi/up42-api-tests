package models

data class ErrorResponse(
    val code: Int,

    val message: String,

    val details: String
)
