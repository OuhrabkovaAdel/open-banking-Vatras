package cz.vatras.openbanking.application

import cz.vatras.openbanking.domain.account.Account
import org.springframework.web.bind.annotation.GetMapping

class AccountService {
    fun getAccounts(): List<Account> {
        return emptyList()
    }
}