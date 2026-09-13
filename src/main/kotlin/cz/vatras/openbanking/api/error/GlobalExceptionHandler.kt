package cz.vatras.openbanking.api.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAccountNotFound(exception: AccountNotFoundException): ErrorResponse {
        return ErrorResponse(
            statusCode = HttpStatus.NOT_FOUND.value(),
            message = exception.message ?: "Account not found"
        )
    }

}