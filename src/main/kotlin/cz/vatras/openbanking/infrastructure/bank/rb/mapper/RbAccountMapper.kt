package cz.vatras.openbanking.infrastructure.bank.rb.mapper

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccountListItem
import org.springframework.stereotype.Component

@Component
class RbAccountMapper {
    fun mapToAccount(rbAccountListItem: RbAccountListItem): Account {
        return Account(
            accountId = rbAccountListItem.accountId,
            iban = rbAccountListItem.iban,
            accountNumber = rbAccountListItem.accountNumber,
            accountNumberPrefix = rbAccountListItem.accountNumberPrefix,
            bankCode = rbAccountListItem.bankCode,
            friendlyName = rbAccountListItem.friendlyName,
            accountType = rbAccountListItem.accountTypeId,
            mainCurrency = rbAccountListItem.mainCurrency
        )
    }

    fun mapToAccounts(rbAccountListItems: List<RbAccountListItem>): List<Account> {
        return rbAccountListItems.map(::mapToAccount)
    }
}