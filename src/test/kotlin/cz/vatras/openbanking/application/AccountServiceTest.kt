package cz.vatras.openbanking.application

import cz.vatras.openbanking.api.error.AccountNotFoundException
import cz.vatras.openbanking.api.request.AccountsRequest
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.balance.Balance
import cz.vatras.openbanking.domain.transaction.Transaction
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.BankAdapterFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate

class AccountServiceTest {

    private val bankAdapterFactory: BankAdapterFactory = mock()

    private val service = AccountService(
        bankAdapterFactory
    )

    @Test
    suspend fun `should get accounts from selected bank`() {
        val bankAdapter = mock<BankAdapter>()

        whenever(
            bankAdapterFactory.getBankAdapter(Bank.RB)
        ).thenReturn(bankAdapter)

        val account = Account(
            accountId = "123",
            iban = "CZ123",
            accountNumber = "123",
            accountNumberPrefix = null,
            bankCode = "5500",
            friendlyName = "Test Account",
            accountType = "CURRENT",
            mainCurrency = "CZK"
        )

        whenever(bankAdapter.getAccounts())
            .thenReturn(listOf(account))

        val result = service.getAccounts(
            AccountsRequest(Bank.RB)
        )

        assertEquals(listOf(account), result)
        verify(bankAdapterFactory).getBankAdapter(Bank.RB)
    }

    @Test
    suspend fun `should get balances for existing account`() {
        val bankAdapter = mock<BankAdapter>()

        whenever(
            bankAdapterFactory.getBankAdapter(Bank.RB)
        ).thenReturn(bankAdapter)

        val account = Account(
            accountId = "123",
            iban = "CZ123",
            accountNumber = "123",
            accountNumberPrefix = null,
            bankCode = "5500",
            friendlyName = "Test Account",
            accountType = "CURRENT",
            mainCurrency = "CZK"
        )

        whenever(bankAdapter.getAccounts())
            .thenReturn(listOf(account))

        val balance = Balance(
            amount = BigDecimal("1000.50"),
            currency = "CZK",
            balanceType = "CLAV",
            creditDebitIndicator = "CRDT"
        )

        whenever(
            bankAdapter.getBalances(account)
        ).thenReturn(listOf(balance))

        val result = service.getBalances(Bank.RB, account.accountId)

        assertEquals(listOf(balance), result)
        verify(bankAdapter).getBalances(account)
    }

    @Test
    suspend fun `should throw exception when account does not exist`() {
        val bankAdapter = mock<BankAdapter>()

        whenever(
            bankAdapterFactory.getBankAdapter(Bank.RB)
        ).thenReturn(bankAdapter)

        whenever(bankAdapter.getAccounts())
            .thenReturn(emptyList())

        val exception = assertThrows<AccountNotFoundException> {
            service.getBalances(Bank.RB, "123")
        }

        assertEquals("Account not found: 123", exception.message)
    }

    @Test
    suspend fun `should get transactions for existing account`() {
        // Arrange
        val bankAdapter = mock<BankAdapter>()

        whenever(
            bankAdapterFactory.getBankAdapter(Bank.RB)
        ).thenReturn(bankAdapter)

        val account = Account(
            accountId = "123",
            iban = "CZ123",
            accountNumber = "123",
            accountNumberPrefix = null,
            bankCode = "5500",
            friendlyName = "Test Account",
            accountType = "CURRENT",
            mainCurrency = "CZK"
        )

        whenever(bankAdapter.getAccounts())
            .thenReturn(listOf(account))

        val transaction = Transaction(
            transactionId = "TX-123",
            amount = BigDecimal("250.00"),
            currency = "CZK",
            creditDebitIndicator = "CRDT",
            bookingDate = LocalDate.of(2026, 1, 15),
            valueDate = LocalDate.of(2026, 1, 15),
            bankTransactionCode = "10000401000",
            reference = "VS123456",
            description = "Test transaction",
            counterparty = null
        )

        whenever(
            bankAdapter.getTransactions(account)
        ).thenReturn(listOf(transaction))

        val result = service.getTransactions(
            Bank.RB,
            account.accountId
        )

        assertEquals(listOf(transaction), result)
        verify(bankAdapter).getTransactions(account)
    }

    @Test
    suspend fun `should throw exception when account does not exist for transactions`() {
        val bankAdapter = mock<BankAdapter>()

        whenever(
            bankAdapterFactory.getBankAdapter(Bank.RB)
        ).thenReturn(bankAdapter)

        whenever(bankAdapter.getAccounts())
            .thenReturn(emptyList())

        val exception = assertThrows<AccountNotFoundException> {
            service.getTransactions(Bank.RB, "123")
        }

        assertEquals("Account not found: 123", exception.message)
    }
}
