package cz.vatras.openbanking.infrastructure.bank.csas.model

data class CsasAccountsResponse(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val nextPage: Int?,
    val accounts: List<CsasAccountListItem>
)

data class CsasAccountListItem(
    val id: String,
    val identification: CsasAccountIdentification,
    val currency: String,
    val servicer: CsasAccountServicer,
    val nameI18N: String?,
    val productI18N: String?,
    val ownersNames: List<String>,
    val relationship: CsasAccountRelationship,
    val suitableScope: Map<String, Any>
)

data class CsasAccountIdentification(
    val iban: String,
    val other: String
)

data class CsasAccountServicer(
    val bankCode: String,
    val countryCode: String,
    val bic: String
)

data class CsasAccountRelationship(
    val isOwner: Boolean
)