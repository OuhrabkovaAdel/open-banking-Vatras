package cz.vatras.openbanking.api

import cz.vatras.openbanking.api.request.AccountsRequest
import cz.vatras.openbanking.application.AccountService
import cz.vatras.openbanking.application.Bank
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var accountService: AccountService

    @Test
    suspend fun `should get accounts from real service`() {
        val result = accountService.getAccounts(
            AccountsRequest(Bank.RB)
        )

        assertEquals(2, result.size)
    }

    @Test
    fun `should return accounts for selected bank`() {
        val result = mockMvc.perform(
            get("/api/v1/accounts")
                .param("bank", "RB")
        )
            .andExpect(status().isOk)
            .andReturn()

        mockMvc.perform(asyncDispatch(result))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].accountId").value("123456789"))
            .andExpect(jsonPath("$[1].accountId").value("987654321"))
    }

    @Test
    fun `should return bad request for invalid bank`() {
        mockMvc.perform(
            get("/api/v1/accounts")
                .param("bank", "INVALID")
        )
            .andExpect(status().isBadRequest)
    }
}