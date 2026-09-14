package cz.vatras.openbanking.api



import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.csas.CsasBankAdapter
import cz.vatras.openbanking.infrastructure.bank.csas.client.CsasApiClient
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasAccountMapper
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasBalanceMapper
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasTransactionMapper
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountsResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CsasBankAdapterTest {

    private val csasApiClient: CsasApiClient = mock()
    private val csasBalanceMapper: CsasBalanceMapper = mock()
    private val csasAccountMapper: CsasAccountMapper = mock()
    private val csasTransactionMapper: CsasTransactionMapper = mock()

    private val adapter = CsasBankAdapter(
        csasAccountMapper,
        csasBalanceMapper,
        csasTransactionMapper,
        csasApiClient
    )
    @Test
    suspend fun `should get accounts`() {
        val csasAccountsResponse = mock<CsasAccountsResponse>()
        val accounts = listOf(
            mock<Account>()
        )

        whenever(csasApiClient.getAccounts())
            .thenReturn(csasAccountsResponse)

        whenever(csasAccountMapper.mapToAccounts(csasAccountsResponse.accounts))
            .thenReturn(accounts)

        val result = adapter.getAccounts()

        assertEquals(accounts, result)
    }
}