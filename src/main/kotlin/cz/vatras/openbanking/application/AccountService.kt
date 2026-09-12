package cz.vatras.openbanking.application

import cz.vatras.openbanking.api.request.AccountsRequest
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.BankAdapterFactory
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val bankAdapterFactory: BankAdapterFactory
) {
    suspend fun getAccounts(request: AccountsRequest): List<Account> {
        val adapter = bankAdapterFactory.getBankAdapter(request.bank)
        return adapter.getAccounts()
    }
}