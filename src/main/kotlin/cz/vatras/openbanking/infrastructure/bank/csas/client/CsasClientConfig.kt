package cz.vatras.openbanking.infrastructure.bank.csas.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class CsasClientConfig {

    @Bean
    fun csasRestClient(): RestClient {
        return RestClient.builder()
            .baseUrl("https://webapi.developers.erstegroup.com")
            .build()
    }
}