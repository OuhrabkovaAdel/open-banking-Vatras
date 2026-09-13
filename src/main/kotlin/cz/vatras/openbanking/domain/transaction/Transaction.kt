package cz.vatras.openbanking.domain.transaction

import java.math.BigDecimal
import java.time.LocalDate

data class Transaction(
    val transactionId: String,
    val amount: BigDecimal,
    val currency: String,
    val creditDebitIndicator: String,
    val bookingDate: LocalDate,
    val valueDate: LocalDate?,
    val bankTransactionCode: String?,
    val reference: String?,
    val description: String?,
    val counterparty: Party?
)