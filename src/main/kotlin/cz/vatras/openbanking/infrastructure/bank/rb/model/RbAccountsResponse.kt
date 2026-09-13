package cz.vatras.openbanking.infrastructure.bank.rb.model

data class RbAccountsResponse(
    val page: Int,
    val size: Int,
    val first: Boolean,
    val last: Boolean,
    val totalPages: Int,
    val totalSize: Int,
    val accounts: List<RbAccountListItem>
)
data class RbAccountListItem(
    val accountId: String,
    val accountName: String,
    val friendlyName: String,
    val accountNumber: String,
    val accountNumberPrefix: String,
    val iban: String,
    val bankCode: String,
    val bankBicCode: String,
    val mainCurrency: String,
    val accountTypeId: String
)