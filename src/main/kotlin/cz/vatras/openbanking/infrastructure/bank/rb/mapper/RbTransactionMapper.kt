package cz.vatras.openbanking.infrastructure.bank.rb.mapper

import cz.vatras.openbanking.domain.transaction.Party
import cz.vatras.openbanking.domain.transaction.Transaction
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransactionResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class RbTransactionMapper {
    private val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun mapToTransactions(rbTransactionResponse: RbTransactionResponse): List<Transaction> {
        return rbTransactionResponse.transactions.map { rbTransaction ->
            Transaction(
                transactionId = rbTransaction.entryReference.toString(),
                amount = rbTransaction.amount.value,
                currency = rbTransaction.amount.currency,
                creditDebitIndicator = rbTransaction.creditDebitIndication,
                bookingDate = LocalDate.parse(rbTransaction.bookingDate, dateFormatter),
                valueDate = LocalDate.parse(rbTransaction.valueDate, dateFormatter),
                bankTransactionCode = rbTransaction.bankTransactionCode.code,
                reference = rbTransaction.entryDetails.transactionDetails.references.endToEndIdentification,
                description = rbTransaction.entryDetails.transactionDetails.remittanceInformation.unstructured,
                counterparty = Party(
                    name = rbTransaction.entryDetails.transactionDetails.relatedParties.counterParty.name,
                    iban = rbTransaction.entryDetails.transactionDetails.relatedParties.counterParty.account.iban,
                    accountNumber = rbTransaction.entryDetails.transactionDetails.relatedParties.counterParty.account.accountNumber,
                    bankCode = rbTransaction.entryDetails.transactionDetails.relatedParties.counterParty.organisationIdentification.bankCode
                )
            )
        }
    }
}
