package cz.vatras.openbanking.api.error

data class ErrorResponse(
    val statusCode: Int,
    val message: String
)