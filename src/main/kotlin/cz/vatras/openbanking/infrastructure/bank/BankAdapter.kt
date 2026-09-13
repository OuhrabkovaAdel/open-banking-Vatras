package cz.vatras.openbanking.infrastructure.bank

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.account.Balance

interface BankAdapter {
    suspend fun getAccounts(): List<Account>
    suspend fun getBalances(account: Account): List<Balance>
}