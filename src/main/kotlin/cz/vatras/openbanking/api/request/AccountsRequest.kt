package cz.vatras.openbanking.api.request

import cz.vatras.openbanking.application.Bank

data class AccountsRequest(
    val bank: Bank
)