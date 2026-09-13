package cz.vatras.openbanking.domain.account

import java.math.BigDecimal

data class Balance(
    val amount: BigDecimal,
    val currency: String,
    val balanceType: String,
    val creditDebitIndicator: String?
)