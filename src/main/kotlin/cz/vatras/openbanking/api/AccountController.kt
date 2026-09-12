package cz.vatras.openbanking.api

import cz.vatras.openbanking.application.AccountService
import cz.vatras.openbanking.domain.account.Account
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountService: AccountService
)
{
    @GetMapping
    suspend fun getAccounts(): List<Account> {
        return accountService.getAccounts()
    }
}