package cz.vatras.openbanking.infrastructure.bank

import cz.vatras.openbanking.domain.account.Account

interface BankAdapter {
    suspend fun getAccounts(): List<Account>
}