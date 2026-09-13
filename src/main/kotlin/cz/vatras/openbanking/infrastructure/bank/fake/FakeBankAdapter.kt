package cz.vatras.openbanking.infrastructure.bank.fake

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.account.Balance
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class FakeBankAdapter : BankAdapter {
    override suspend fun getAccounts(): List<Account> {
        return listOf(
            Account(
                accountId = "1",
                iban = "123456789",
                accountNumber = "123456789",
                accountNumberPrefix = null,
                bankCode = "0100",
                friendlyName = "Main Account",
                accountType = "CURRENT",
                mainCurrency = "CZK"
            ),
            Account(
                accountId = "2",
                iban = "0123456789",
                accountNumber = "0123456789",
                accountNumberPrefix = null,
                bankCode = "0100",
                friendlyName = "Savings Account",
                accountType = "SAVINGS",
                mainCurrency = "CZK"
            )
        )
    }

    override suspend fun getBalances(account: Account): List<Balance> {
        return listOf(
            Balance(
                amount = BigDecimal("1000.0"),
                currency = "CZK",
                balanceType = "CLAB",
                creditDebitIndicator = null
            ),
            Balance(
                amount = BigDecimal("500.0"),
                currency = "EUR",
                balanceType = "CLAB",
                creditDebitIndicator = null
            )
        )
    }
}