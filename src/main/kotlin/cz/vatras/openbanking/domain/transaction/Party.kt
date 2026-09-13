package cz.vatras.openbanking.domain.transaction

data class Party(
    val name: String?,
    val iban: String?,
    val accountNumber: String?,
    val bankCode: String?
)