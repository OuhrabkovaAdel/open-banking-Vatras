package cz.vatras.openbanking.infrastructure.bank.csas.mapper

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountListItem
import org.springframework.stereotype.Component

@Component
class CsasAccountMapper {

    fun mapToAccount(csasAccountListItem: CsasAccountListItem): Account {
        return Account(
            accountId = csasAccountListItem.id,
            iban = csasAccountListItem.identification.iban,
            accountNumber = csasAccountListItem.identification.other,
            accountNumberPrefix = null,
            bankCode = csasAccountListItem.servicer.bankCode,
            friendlyName = csasAccountListItem.nameI18N,
            accountType = csasAccountListItem.productI18N,
            mainCurrency = csasAccountListItem.currency
        )
    }

    fun mapToAccounts(csasAccountListItems: List<CsasAccountListItem>): List<Account> {
        return csasAccountListItems.map(::mapToAccount)
    }
}