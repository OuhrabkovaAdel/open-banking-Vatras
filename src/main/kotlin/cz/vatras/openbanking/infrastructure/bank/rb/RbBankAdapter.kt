package cz.vatras.openbanking.infrastructure.bank.rb
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.account.Balance
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.rb.mapper.RbAccountMapper
import cz.vatras.openbanking.infrastructure.bank.rb.mapper.RbBalanceMapper
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccountListItem
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCurrencyBalance
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbBalanceResponse
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCurrencyFolder
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class RbBankAdapter(
    private val rbAccountMapper: RbAccountMapper,
    private val rbBalanceMapper: RbBalanceMapper
) : BankAdapter {
    override suspend fun getAccounts(): List<Account> {
        val rbAccountListItems = listOf(
            RbAccountListItem(
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
            ),
            RbAccountListItem(
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
        return rbAccountMapper.mapToAccounts(rbAccountListItems)
    }

    override suspend fun getBalances(account: Account): List<Balance> {
        val rbBalanceResponse = RbBalanceResponse(
            numberPart1 = "19",
            numberPart2 = "6760653036",
            bankCode = "5500",
            currencyFolders = listOf(
                RbCurrencyFolder(
                    currency = "CZK",
                    status = "ACTIVE",
                    balances = listOf(
                        RbCurrencyBalance(
                            balanceType = "CLAB",
                            currency = "CZK",
                            value = BigDecimal("1000.5")
                        )
                    )
                ),
                RbCurrencyFolder(
                    currency = "EUR",
                    status = "ACTIVE",
                    balances = listOf(
                        RbCurrencyBalance(
                            balanceType = "CLAB",
                            currency = "EUR",
                            value = BigDecimal("500.25")
                        )
                    )
                )
            )
        )
        return rbBalanceMapper.mapToBalances(rbBalanceResponse)
    }
}