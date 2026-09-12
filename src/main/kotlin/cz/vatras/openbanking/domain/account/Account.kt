package cz.vatras.openbanking.domain.account

import java.math.BigDecimal

data class Account(
    val id: String,
    val iban: String?,
    val name: String?,
    val currency: String,
    val balance: Balance
)