package cz.vatras.openbanking.api.error

class AccountNotFoundException(
    accountId: String
) : RuntimeException("Account not found: $accountId")