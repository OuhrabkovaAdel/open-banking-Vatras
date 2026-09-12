package cz.vatras.openbanking.infrastructure.bank.csas.mapper

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountResponse
import org.springframework.stereotype.Component

@Component
class CsasAccountMapper {
    fun mapToAccount(csasAccountResponse: CsasAccountResponse): Account {
        return Account(
            accountId = csasAccountResponse.id,
            iban = csasAccountResponse.identification.iban,
            accountNumber = csasAccountResponse.identification.other,
            accountNumberPrefix = null,
            bankCode = csasAccountResponse.servicer.bankCode,
            friendlyName = csasAccountResponse.nameI18N,
            accountType = csasAccountResponse.productI18N,
            mainCurrency = csasAccountResponse.currency
        )
    }
}