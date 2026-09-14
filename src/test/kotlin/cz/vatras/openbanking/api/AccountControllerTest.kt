package cz.vatras.openbanking.api

import cz.vatras.openbanking.api.request.AccountsRequest
import cz.vatras.openbanking.application.AccountService
import cz.vatras.openbanking.application.Bank
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.balance.Balance
import cz.vatras.openbanking.domain.transaction.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate

class AccountControllerTest {

    private val accountService: AccountService = mock()

    private val controller = AccountController(
        accountService
    )

    @Test
    suspend fun `should get accounts from selected bank`() {

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

        whenever(
            accountService.getAccounts(AccountsRequest(Bank.RB))
        ).thenReturn(listOf(account))

        val result = controller.getAccounts(Bank.RB)

        assertEquals(listOf(account), result)
        verify(accountService).getAccounts(AccountsRequest(Bank.RB))
    }

    @Test
    suspend fun `should get balances for selected account`() {

        val balance = Balance(
            amount = BigDecimal("1000.50"),
            currency = "CZK",
            balanceType = "CLAV",
            creditDebitIndicator = "CRDT"
        )

        whenever(
            accountService.getBalances(Bank.RB, "123")
        ).thenReturn(listOf(balance))

        val result = controller.getBalances("123", Bank.RB)

        assertEquals(listOf(balance), result)
        verify(accountService).getBalances(Bank.RB, "123")
    }

    @Test
    suspend fun `should get transactions for selected account`() {

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
            accountService.getTransactions(Bank.RB, "123")
        ).thenReturn(listOf(transaction))

        val result = controller.getTransactions("123", Bank.RB)

        assertEquals(listOf(transaction), result)
        verify(accountService).getTransactions(Bank.RB, "123")
    }
}