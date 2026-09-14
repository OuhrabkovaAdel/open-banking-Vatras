package cz.vatras.openbanking.infrastructure.bank.csas.client
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountsResponse

interface CsasApiClient {
    fun getAccounts(): CsasAccountsResponse
}