package cz.vatras.openbanking.infrastructure.bank.csas.client

import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountsResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class CsasApiClientImpl(
    private val csasRestClient: RestClient
) : CsasApiClient {

    override fun getAccounts(): CsasAccountsResponse {
        return csasRestClient.get()
            .uri("/api/egb/sandbox/v1/aisp/v1/accounts")
            .retrieve()
            .body(CsasAccountsResponse::class.java)!!
    }
}