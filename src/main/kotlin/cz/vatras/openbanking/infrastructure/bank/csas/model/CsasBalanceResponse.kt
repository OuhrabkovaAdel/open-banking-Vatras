package cz.vatras.openbanking.infrastructure.bank.csas.model

import java.math.BigDecimal

data class CsasBalanceResponse(
    val balances: List<CsasBalance>
)

data class CsasBalance(
    val type: CsasBalanceType,
    val amount: CsasBalanceAmount,
    val creditDebitIndicator: String,
    val date: CsasBalanceDate
)

data class CsasBalanceType(
    val codeOrProprietary: CsasCodeOrProprietary
)

data class CsasCodeOrProprietary(
    val code: String
)

data class CsasBalanceAmount(
    val value: BigDecimal,
    val currency: String
)

data class CsasBalanceDate(
    val dateTime: String
)
