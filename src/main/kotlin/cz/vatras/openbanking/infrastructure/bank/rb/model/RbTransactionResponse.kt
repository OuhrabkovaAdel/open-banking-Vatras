package cz.vatras.openbanking.infrastructure.bank.rb.model

import java.math.BigDecimal

data class RbTransactionResponse(
    val lastPage: Boolean,
    val transactions: List<RbTransaction>
)

data class RbTransaction(
    val entryReference: String,
    val amount: RbAmount,
    val creditDebitIndication: String,
    val bookingDate: String,
    val valueDate: String,
    val bankTransactionCode: RbBankTransactionCode,
    val entryDetails: RbEntryDetails
)

data class RbAmount(
    val value: BigDecimal,
    val currency: String
)

data class RbBankTransactionCode(
    val code: String
)

data class RbEntryDetails(
    val transactionDetails: RbTransactionDetails
)

data class RbTransactionDetails(
    val references: RbReferences,
    val instructedAmount: RbInstructedAmount,
    val chargeBearer: String,
    val paymentCardNumber: String?,
    val relatedParties: RbRelatedParties,
    val remittanceInformation: RbRemittanceInformation
)

data class RbReferences(
    val endToEndIdentification: String
)

data class RbInstructedAmount(
    val value: BigDecimal,
    val currency: String,
    val exchangeRate: BigDecimal
)

data class RbRelatedParties(
    val counterParty: RbCounterParty,
    val intermediaryInstitution: RbIntermediaryInstitution,
    val ultimateCounterParty: RbUltimateCounterParty
)

data class RbCounterParty(
    val name: String,
    val postalAddress: RbPostalAddress,
    val organisationIdentification: RbOrganisationIdentification,
    val account: RbAccount
)

data class RbPostalAddress(
    val street: String,
    val city: String,
    val country: String
)

data class RbOrganisationIdentification(
    val name: String,
    val bicOrBei: String,
    val bankCode: String,
    val postalAddress: RbPostalAddress
)

data class RbAccount(
    val iban: String,
    val accountNumberPrefix: String,
    val accountNumber: String
)

data class RbIntermediaryInstitution(
    val name: String,
    val bicOrBei: String,
    val bankCode: String,
    val postalAddress: RbPostalAddress
)

data class RbUltimateCounterParty(
    val name: String,
    val postalAddress: RbPostalAddress
)

data class RbRemittanceInformation(
    val unstructured: String,
    val creditorReferenceInformation: RbCreditorReferenceInformation,
    val originatorMessage: String
)

data class RbCreditorReferenceInformation(
    val variable: String,
    val constant: String,
    val specific: String
)
