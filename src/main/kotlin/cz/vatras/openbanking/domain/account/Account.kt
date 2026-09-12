package cz.vatras.openbanking.domain.account

import java.math.BigDecimal

data class Account(
    val accountId: String,
    val iban: String?,
    val accountNumber: String,
    val accountNumberPrefix: String?,
    val bankCode: String,
    val friendlyName: String?,
    val accountType: String?,
    val mainCurrency: String
)