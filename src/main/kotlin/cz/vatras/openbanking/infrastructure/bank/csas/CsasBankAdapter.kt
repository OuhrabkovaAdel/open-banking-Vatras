package cz.vatras.openbanking.infrastructure.bank.csas
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasAccountMapper
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountRelationship
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountResponse
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountServicer
import org.springframework.stereotype.Component

@Component
class CsasBankAdapter(
    private val csasAccountMapper: CsasAccountMapper
) : BankAdapter {
    override suspend fun getAccounts(): List<Account> {
        return listOf(
            csasAccountMapper.mapToAccount(
                CsasAccountResponse(
                    id = "123456789",
                    identification = CsasAccountIdentification(
                        iban = "CZ6508000000001234567890",
                        other = "123456789"
                    ),
                    currency = "CZK",
                    servicer = CsasAccountServicer(
                        bankCode = "0800",
                        countryCode = "CZ",
                        bic = "CZKOCZ1X"
                    ),
                    nameI18N = "Personal Account",
                    productI18N = "CURRENT",
                    ownersNames = listOf("John Doe"),
                    relationship = CsasAccountRelationship(isOwner = true),
                    suitableScope = emptyMap()
                )
            ),
            csasAccountMapper.mapToAccount(
                CsasAccountResponse(
                    id = "987654321",
                    identification = CsasAccountIdentification(
                        iban = "CZ6508000000009876543210",
                        other = "987654321"
                    ),
                    currency = "CZK",
                    servicer = CsasAccountServicer(
                        bankCode = "0800",
                        countryCode = "CZ",
                        bic = "CZKOCZ1X"
                    ),
                    nameI18N = "Savings Account",
                    productI18N = "SAVINGS",
                    ownersNames = listOf("John Doe"),
                    relationship = CsasAccountRelationship(isOwner = true),
                    suitableScope = emptyMap()
                )
            )
        )
    }
}