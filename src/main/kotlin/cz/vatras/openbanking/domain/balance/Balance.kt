package cz.vatras.openbanking.domain.balance

import java.math.BigDecimal

data class Balance(
    val amount: BigDecimal,
    val currency: String,
    val balanceType: String,
    val creditDebitIndicator: String?
)