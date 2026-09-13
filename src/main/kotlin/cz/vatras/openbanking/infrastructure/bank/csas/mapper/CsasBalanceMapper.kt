package cz.vatras.openbanking.infrastructure.bank.csas.mapper

import cz.vatras.openbanking.domain.balance.Balance
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceResponse
import org.springframework.stereotype.Component

@Component
class CsasBalanceMapper {
    fun mapToBalances(csasBalanceResponse: CsasBalanceResponse): List<Balance> {
        return csasBalanceResponse.balances.map { balance ->
            Balance(
                amount = balance.amount.value,
                currency = balance.amount.currency,
                balanceType = balance.type.codeOrProprietary.code,
                creditDebitIndicator = balance.creditDebitIndicator
            )
        }
    }
}
