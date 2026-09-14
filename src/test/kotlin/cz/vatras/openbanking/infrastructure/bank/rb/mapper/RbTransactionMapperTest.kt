package cz.vatras.openbanking.infrastructure.bank.rb.mapper

import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccount
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAmount
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbBankTransactionCode
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCounterParty
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCreditorReferenceInformation
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbEntryDetails
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbInstructedAmount
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbIntermediaryInstitution
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbOrganisationIdentification
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbPostalAddress
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbReferences
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbRelatedParties
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbRemittanceInformation
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransaction
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransactionDetails
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransactionResponse
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbUltimateCounterParty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class RbTransactionMapperTest {

    private val mapper = RbTransactionMapper()

    @Test
    fun `should map transaction to domain model`() {
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
            "2108589434",
            transaction.counterparty?.accountNumber
        )
        assertEquals("0800", transaction.counterparty?.bankCode)
    }

    @Test
    fun `should preserve debit transaction indicator`() {
        val response = createTransactionResponse("DBIT")

        val result = mapper.mapToTransactions(response)

        val transaction = result.first()

        assertEquals("DBIT", transaction.creditDebitIndicator)
        assertEquals("Hlobil Ferdinand", transaction.counterparty?.name)
    }

    private fun createTransactionResponse(
        creditDebitIndication: String
    ): RbTransactionResponse {
        return RbTransactionResponse(
            lastPage = true,
            transactions = listOf(
                RbTransaction(
                    entryReference = "FC-4567513951",
                    amount = RbAmount(
                        value = BigDecimal("4520.15"),
                        currency = "CZK"
                    ),
                    creditDebitIndication = creditDebitIndication,
                    bookingDate = "2019-10-20T00:00:00+02:00",
                    valueDate = "2019-10-20T00:00:00+02:00",
                    bankTransactionCode = RbBankTransactionCode(
                        code = "10000202000"
                    ),
                    entryDetails = RbEntryDetails(
                        transactionDetails = RbTransactionDetails(
                            references = RbReferences(
                                endToEndIdentification = "VS0250117002/SS0000000000/KS0000"
                            ),
                            instructedAmount = RbInstructedAmount(
                                value = BigDecimal("4520.15"),
                                currency = "CZK",
                                exchangeRate = BigDecimal("1.0")
                            ),
                            chargeBearer = "OUR",
                            paymentCardNumber = null,
                            relatedParties = RbRelatedParties(
                                counterParty = createCounterParty(),
                                intermediaryInstitution = createIntermediaryInstitution(),
                                ultimateCounterParty = createUltimateCounterParty()
                            ),
                            remittanceInformation = RbRemittanceInformation(
                                unstructured = "faktura 12345",
                                creditorReferenceInformation = RbCreditorReferenceInformation(
                                    variable = "123456",
                                    constant = "0000",
                                    specific = "0000"
                                ),
                                originatorMessage = "Tuzemská odchozí úhrada"
                            )
                        )
                    )
                )
            )
        )
    }

    private fun createCounterParty(): RbCounterParty {
        return RbCounterParty(
            name = "Hlobil Ferdinand",
            postalAddress = RbPostalAddress(
                street = "Main Street",
                city = "Prague",
                country = "CZ"
            ),
            organisationIdentification = RbOrganisationIdentification(
                name = "Bank",
                bicOrBei = "GIBACZPXXXX",
                bankCode = "0800",
                postalAddress = RbPostalAddress(
                    street = "Bank Street",
                    city = "Prague",
                    country = "CZ"
                )
            ),
            account = RbAccount(
                iban = "CZ0827000000002108589434",
                accountNumberPrefix = "000000",
                accountNumber = "2108589434"
            )
        )
    }

    private fun createIntermediaryInstitution(): RbIntermediaryInstitution {
        return RbIntermediaryInstitution(
            name = "Intermediary Bank",
            bicOrBei = "GIBACZPXXXX",
            bankCode = "0800",
            postalAddress = RbPostalAddress(
                street = "Bank Street",
                city = "Prague",
                country = "CZ"
            )
        )
    }

    private fun createUltimateCounterParty(): RbUltimateCounterParty {
        return RbUltimateCounterParty(
            name = "Ultimate Party",
            postalAddress = RbPostalAddress(
                street = "Ultimate Street",
                city = "Prague",
                country = "CZ"
            )
        )
    }
}
