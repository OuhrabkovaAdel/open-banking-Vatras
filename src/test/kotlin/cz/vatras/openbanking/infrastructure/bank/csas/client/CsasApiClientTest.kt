package cz.vatras.openbanking.infrastructure.bank.csas.client

import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountsResponse
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.web.client.RestClient

class CsasApiClientTest {

    private val restClient: RestClient = mock()

    private val client = CsasApiClientImpl(
        restClient
    )

    @Test
    fun `should get accounts`() {
        val request = mock<RestClient.RequestHeadersUriSpec<*>>()
        val response = mock<RestClient.ResponseSpec>()
        val expectedResponse = mock<CsasAccountsResponse>()

        whenever(restClient.get()).thenReturn(request)

        whenever(request.uri("/api/egb/sandbox/v1/aisp/v1/accounts"))
            .thenReturn(request)

        whenever(request.retrieve()).thenReturn(response)

        whenever(response.body(CsasAccountsResponse::class.java))
            .thenReturn(expectedResponse)

        val result = client.getAccounts()

        assert(result == expectedResponse)
        verify(restClient).get()
        verify(request).uri("/api/egb/sandbox/v1/aisp/v1/accounts")
        verify(request).retrieve()
        verify(response).body(CsasAccountsResponse::class.java)
    }
}