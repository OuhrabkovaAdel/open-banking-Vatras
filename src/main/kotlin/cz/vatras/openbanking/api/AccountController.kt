package cz.vatras.openbanking.api

import cz.vatras.openbanking.api.request.AccountsRequest
import cz.vatras.openbanking.application.AccountService
import cz.vatras.openbanking.application.Bank
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.balance.Balance
import cz.vatras.openbanking.domain.transaction.Transaction
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping
    suspend fun getAccounts(
        @RequestParam("bank") bank: Bank
    ): List<Account> {
        return accountService.getAccounts(
            AccountsRequest(bank)
        )
    }

    @GetMapping("/{accountId}/balances")
    suspend fun getBalances(
        @PathVariable accountId: String,
        @RequestParam("bank") bank: Bank
    ): List<Balance> {
        return accountService.getBalances(
            bank,
            accountId
        )
    }

    @GetMapping("/{accountId}/transactions")
    suspend fun getTransactions(
        @PathVariable accountId: String,
        @RequestParam("bank") bank: Bank
    ): List<Transaction> {
        return accountService.getTransactions(
            bank,
            accountId
        )
    }
}