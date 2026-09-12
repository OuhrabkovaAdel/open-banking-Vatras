package cz.vatras.openbanking.infrastructure.bank.rb.mapper

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccountResponse
import org.springframework.stereotype.Component

@Component
class RbAccountMapper {
    fun mapToAccount(rbAccountResponse: RbAccountResponse): Account {
        return Account(
            accountId = rbAccountResponse.accountId,
            iban = rbAccountResponse.iban,
            accountNumber = rbAccountResponse.accountNumber,
            accountNumberPrefix = rbAccountResponse.accountNumberPrefix,
            bankCode = rbAccountResponse.bankCode,
            friendlyName = rbAccountResponse.friendlyName,
            accountType = rbAccountResponse.accountTypeId,
            mainCurrency = rbAccountResponse.mainCurrency
        )
    }
}