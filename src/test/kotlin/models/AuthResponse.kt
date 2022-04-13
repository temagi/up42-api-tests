package models

data class AuthResponse(
    val data: AuthResponseData?,

    val access_token: String?,

    val token_type: String?
)

data class AuthResponseData(
    val accessToken: String?
)
