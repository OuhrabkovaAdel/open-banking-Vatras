package cz.vatras.openbanking.infrastructure.bank.csas.mapper

import cz.vatras.openbanking.domain.transaction.Party
import cz.vatras.openbanking.domain.transaction.Transaction
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransaction
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransactionResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class CsasTransactionMapper {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun mapToTransactions(csasTransactionResponse: CsasTransactionResponse): List<Transaction> {
        return csasTransactionResponse.transactions.map { csasTransaction ->
            Transaction(
                transactionId = csasTransaction.entryReference,
                amount = csasTransaction.amount.value,
                currency = csasTransaction.amount.currency,
                creditDebitIndicator = csasTransaction.creditDebitIndicator,
                bookingDate = LocalDate.parse(csasTransaction.bookingDate.date, dateFormatter),
                valueDate = LocalDate.parse(csasTransaction.valueDate.date, dateFormatter),
                bankTransactionCode = csasTransaction.bankTransactionCode.proprietary.code.toString(),
                reference = csasTransaction.entryDetails.transactionDetails.references.endToEndIdentification,
                description = csasTransaction.entryDetails.transactionDetails.remittanceInformation.unstructured,
                counterparty = mapCounterparty(csasTransaction)
            )
        }
    }

    private fun mapCounterparty(csasTransaction: CsasTransaction): Party {
        val relatedParties = csasTransaction.entryDetails.transactionDetails.relatedParties

        return if (csasTransaction.creditDebitIndicator == "CRDT") {
            Party(
                name = relatedParties.debtor.name,
                iban = relatedParties.debtorAccount.identification.iban,
                accountNumber = relatedParties.debtorAccount.identification.other.identification,
                bankCode = null
            )
        } else {
            Party(
                name = relatedParties.creditor.name,
                iban = relatedParties.creditorAccount.identification.iban,
                accountNumber = relatedParties.creditorAccount.identification.other.identification,
                bankCode = null
            )
        }
    }
}