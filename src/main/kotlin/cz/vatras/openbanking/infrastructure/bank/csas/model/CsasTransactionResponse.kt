package cz.vatras.openbanking.infrastructure.bank.csas.model

import java.math.BigDecimal

data class CsasTransactionResponse(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val nextPage: Int,
    val transactions: List<CsasTransaction>
)

data class CsasTransaction(
    val entryReference: String,
    val reservationId: String,
    val amount: CsasAmount,
    val creditDebitIndicator: String,
    val status: String,
    val bookingDate: CsasDate,
    val valueDate: CsasDate,
    val bankTransactionCode: CsasBankTransactionCode,
    val entryDetails: CsasEntryDetails
)

data class CsasAmount(
    val value: BigDecimal,
    val currency: String
)

data class CsasDate(
    val date: String
)

data class CsasBankTransactionCode(
    val proprietary: CsasProprietary
)

data class CsasProprietary(
    val code: Long,
    val issuer: String
)

data class CsasEntryDetails(
    val transactionDetails: CsasTransactionDetails
)

data class CsasTransactionDetails(
    val references: CsasReferences,
    val amountDetails: CsasAmountDetails,
    val charges: CsasCharges,
    val relatedParties: CsasRelatedParties,
    val relatedAgents: CsasRelatedAgents,
    val remittanceInformation: CsasRemittanceInformation,
    val additionalTransactionInformation: String,
    val additionalRemittanceInformation: String,
    val additionalTransactionDescription: String
)

data class CsasReferences(
    val accountServicerReference: String,
    val endToEndIdentification: String,
    val chequeNumber: String
)

data class CsasAmountDetails(
    val instructedAmount: CsasInstructedAmount,
    val counterValueAmount: CsasCounterValueAmount
)

data class CsasInstructedAmount(
    val amount: CsasAmount
)

data class CsasCounterValueAmount(
    val amount: CsasAmount,
    val currencyExchange: CsasCurrencyExchange
)

data class CsasCurrencyExchange(
    val sourceCurrency: String,
    val targetCurrency: String,
    val exchangeRate: BigDecimal
)

data class CsasCharges(
    val bearer: String
)

data class CsasRelatedParties(
    val debtor: CsasParty,
    val debtorAccount: CsasPartyAccountIdentification,
    val creditor: CsasParty,
    val creditorAccount: CsasPartyAccountIdentification,
    val proprietary: CsasProprietaryParty
)

data class CsasParty(
    val name: String
)

data class CsasPartyAccountIdentification(
    val identification: CsasIdentification
)

data class CsasIdentification(
    val iban: String,
    val other: CsasOther
)

data class CsasOther(
    val identification: String
)

data class CsasProprietaryParty(
    val party: CsasParty
)

data class CsasRelatedAgents(
    val creditorAgent: CsasAgent,
    val debtorAgent: CsasAgent
)

data class CsasAgent(
    val financialInstitutionIdentification: CsasFinancialInstitutionIdentification
)

data class CsasFinancialInstitutionIdentification(
    val bic: String
)

data class CsasRemittanceInformation(
    val unstructured: String,
    val structured: CsasStructured
)

data class CsasStructured(
    val creditorReferenceInformation: CsasCreditorReferenceInformation
)

data class CsasCreditorReferenceInformation(
    val reference: List<String>
)
