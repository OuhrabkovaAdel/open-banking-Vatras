package cz.vatras.openbanking.infrastructure.bank.csas.mapper

import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBankTransactionCode
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasDate
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasEntryDetails
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasOther
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasParty
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasPartyAccountIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasProprietary
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasProprietaryParty
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasRelatedAgents
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasRelatedParties
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasRemittanceInformation
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasReferences
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasStructured
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCreditorReferenceInformation
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransaction
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransactionDetails
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransactionResponse
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAmountDetails
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasInstructedAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCounterValueAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCurrencyExchange
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCharges
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CsasTransactionMapperTest {

    private val mapper = CsasTransactionMapper()

    @Test
    fun `should map credit transaction to debtor as counterparty`() {
        val response = createTransactionResponse("CRDT")

        val result = mapper.mapToTransactions(response)

        assertEquals(1, result.size)

        val transaction = result.first()

        assertEquals("FC-4567513951", transaction.transactionId)
        assertEquals(BigDecimal("4520.15"), transaction.amount)
        assertEquals("CZK", transaction.currency)
        assertEquals("CRDT", transaction.creditDebitIndicator)
        assertEquals("2019-10-20", transaction.bookingDate.toString())
        assertEquals("2019-10-20", transaction.valueDate?.toString())
        assertEquals("10000202000", transaction.bankTransactionCode)
        assertEquals("VS0250117002/SS0000000000/KS0000", transaction.reference)
        assertEquals("faktura 12345", transaction.description)

        assertEquals("Hlobil Ferdinand", transaction.counterparty?.name)
        assertEquals(
            "CZ0827000000002108589434",
            transaction.counterparty?.iban
        )
        assertEquals(
            "000000-2108589434/0800",
            transaction.counterparty?.accountNumber
        )
        assertEquals(null, transaction.counterparty?.bankCode)
    }

    @Test
    fun `should map debit transaction to creditor as counterparty`() {
        val response = createTransactionResponse("DBIT")

        val result = mapper.mapToTransactions(response)

        val transaction = result.first()

        assertEquals("Creditor", transaction.counterparty?.name)

    }

    private fun createTransactionResponse(
        creditDebitIndicator: String
    ): CsasTransactionResponse {
        return CsasTransactionResponse(
            pageNumber = 0,
            pageCount = 1,
            pageSize = 10,
            nextPage = 1,
            transactions = listOf(
                CsasTransaction(
                    entryReference = "FC-4567513951",
                    reservationId = "498765432",
                    amount = CsasAmount(
                        value = BigDecimal("4520.15"),
                        currency = "CZK"
                    ),
                    creditDebitIndicator = creditDebitIndicator,
                    status = "BOOK",
                    bookingDate = CsasDate("2019-10-20"),
                    valueDate = CsasDate("2019-10-20"),
                    bankTransactionCode = CsasBankTransactionCode(
                        proprietary = CsasProprietary(
                            code = 10000202000L,
                            issuer = "Czech Banking Association"
                        )
                    ),
                    entryDetails = CsasEntryDetails(
                        transactionDetails = CsasTransactionDetails(
                            references = CsasReferences(
                                accountServicerReference = "0622510833568",
                                endToEndIdentification =
                                    "VS0250117002/SS0000000000/KS0000",
                                chequeNumber = "451161XXXXXX2638"
                            ),
                            amountDetails = CsasAmountDetails(
                                instructedAmount = CsasInstructedAmount(
                                    amount = CsasAmount(
                                        value = BigDecimal("4520.15"),
                                        currency = "CZK"
                                    )
                                ),
                                counterValueAmount = CsasCounterValueAmount(
                                    amount = CsasAmount(
                                        value = BigDecimal("4520.15"),
                                        currency = "CZK"
                                    ),
                                    currencyExchange = CsasCurrencyExchange(
                                        sourceCurrency = "GBP",
                                        targetCurrency = "CZK",
                                        exchangeRate = BigDecimal("35.525")
                                    )
                                )
                            ),
                            charges = CsasCharges("OUR"),
                            relatedParties = CsasRelatedParties(
                                debtor = CsasParty("Hlobil Ferdinand"),
                                debtorAccount = createAccount(),
                                creditor = CsasParty("Creditor"),
                                creditorAccount = createAccount(),
                                proprietary = CsasProprietaryParty(
                                    party = CsasParty("Bank")
                                )
                            ),
                            relatedAgents = CsasRelatedAgents(
                                creditorAgent = createAgent(),
                                debtorAgent = createAgent()
                            ),
                            remittanceInformation = CsasRemittanceInformation(
                                unstructured = "faktura 12345",
                                structured = CsasStructured(
                                    creditorReferenceInformation =
                                        CsasCreditorReferenceInformation(
                                            reference = listOf("VS:123456")
                                        )
                                )
                            ),
                            additionalTransactionInformation =
                                "Tuzemská odchozí úhrada",
                            additionalRemittanceInformation = "na motorku",
                            additionalTransactionDescription = "SEPA PŘEVOD"
                        )
                    )
                )
            )
        )
    }

    private fun createAccount(): CsasPartyAccountIdentification {
        return CsasPartyAccountIdentification(
            identification = CsasIdentification(
                iban = "CZ0827000000002108589434",
                other = CsasOther(
                    identification = "000000-2108589434/0800"
                )
            )
        )
    }

    private fun createAgent(): cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAgent {
        return cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAgent(
            financialInstitutionIdentification =
                cz.vatras.openbanking.infrastructure.bank.csas.model.CsasFinancialInstitutionIdentification(
                    bic = "GIBACZPXXXX"
                )
        )
    }

}