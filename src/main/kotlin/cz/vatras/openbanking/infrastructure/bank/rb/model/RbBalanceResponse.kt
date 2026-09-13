package cz.vatras.openbanking.infrastructure.bank.rb.model

import java.math.BigDecimal

data class RbBalanceResponse(
    val numberPart1: String,
    val numberPart2: String,
    val bankCode: String,
    val currencyFolders: List<RbCurrencyFolder>
)

data class RbCurrencyFolder(
    val currency: String,
    val status: String,
    val balances: List<RbCurrencyBalance>
)

data class RbCurrencyBalance(
    val balanceType: String,
    val currency: String,
    val value: BigDecimal
)
