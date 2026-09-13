package cz.vatras.openbanking.infrastructure.bank.rb.mapper

import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.account.Balance
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbBalanceResponse
import org.springframework.stereotype.Component

@Component
class RbBalanceMapper {

    fun mapToBalances(rbBalanceResponse: RbBalanceResponse): List<Balance> {
        return rbBalanceResponse.currencyFolders.flatMap { currencyFolder ->
            currencyFolder.balances.map { balance ->
                Balance(
                    amount = balance.value,
                    currency = balance.currency,
                    balanceType = balance.balanceType,
                    creditDebitIndicator = null
                )
            }
        }
    }
}
