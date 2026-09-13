package cz.vatras.openbanking.infrastructure.bank.csas
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.account.Balance
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasAccountMapper
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasBalanceMapper
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountRelationship
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountListItem
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountServicer
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalance
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceDate
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceResponse
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceType
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCodeOrProprietary
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CsasBankAdapter(
    private val csasAccountMapper: CsasAccountMapper,
    private val csasBalanceMapper: CsasBalanceMapper
) : BankAdapter {
    override suspend fun getAccounts(): List<Account> {
        val csasAccountListItems = listOf(
            CsasAccountListItem(
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
            ),
            CsasAccountListItem(
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
        return csasAccountMapper.mapToAccounts(csasAccountListItems)
    }

    override suspend fun getBalances(account: Account): List<Balance> {
        val csasBalanceResponse = CsasBalanceResponse(
            balances = listOf(
                CsasBalance(
                    type = CsasBalanceType(
                        codeOrProprietary = CsasCodeOrProprietary(
                            code = "CLAV"
                        )
                    ),
                    amount = CsasBalanceAmount(
                        value = BigDecimal("4520.15"),
                        currency = "CZK"
                    ),
                    creditDebitIndicator = "DBIT",
                    date = CsasBalanceDate(
                        dateTime = "2017-05-30T05:37:30+02:00"
                    )
                ),
                CsasBalance(
                    type = CsasBalanceType(
                        codeOrProprietary = CsasCodeOrProprietary(
                            code = "CLAV"
                        )
                    ),
                    amount = CsasBalanceAmount(
                        value = BigDecimal("250.75"),
                        currency = "EUR"
                    ),
                    creditDebitIndicator = "DBIT",
                    date = CsasBalanceDate(
                        dateTime = "2017-05-30T05:37:30+02:00"
                    )
                )
            )
        )
        return csasBalanceMapper.mapToBalances(csasBalanceResponse)
    }
}