package cz.vatras.openbanking.infrastructure.bank.rb

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.rb.mapper.RbAccountMapper
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccountResponse
import org.springframework.stereotype.Component

@Component
class RbBankAdapter(
    private val rbAccountMapper: RbAccountMapper
) : BankAdapter {
    override suspend fun getAccounts(): List<Account> {
        return listOf(
            rbAccountMapper.mapToAccount(
                RbAccountResponse(
                    accountId = "123456789",
                    accountName = "Personal Account",
                    friendlyName = "My Main Account",
                    accountNumber = "123456789",
                    accountNumberPrefix = "0000",
                    iban = "CZ6508000000001234567890",
                    bankCode = "0800",
                    bankBicCode = "CZKOCZ1X",
                    mainCurrency = "CZK",
                    accountTypeId = "CURRENT"
                )
            ),
            rbAccountMapper.mapToAccount(
                RbAccountResponse(
                    accountId = "987654321",
                    accountName = "Savings Account",
                    friendlyName = "My Savings",
                    accountNumber = "987654321",
                    accountNumberPrefix = "0000",
                    iban = "CZ6508000000009876543210",
                    bankCode = "0800",
                    bankBicCode = "CZKOCZ1X",
                    mainCurrency = "CZK",
                    accountTypeId = "SAVINGS"
                )
            )
        )
    }
}