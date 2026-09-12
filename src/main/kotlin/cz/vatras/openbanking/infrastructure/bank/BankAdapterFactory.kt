package cz.vatras.openbanking.infrastructure.bank

import cz.vatras.openbanking.application.Bank
import cz.vatras.openbanking.infrastructure.bank.csas.CsasBankAdapter
import cz.vatras.openbanking.infrastructure.bank.rb.RbBankAdapter
import org.springframework.stereotype.Component

@Component
class BankAdapterFactory(
    private val rbBankAdapter: RbBankAdapter,
    private val csasBankAdapter: CsasBankAdapter
) {
    fun getBankAdapter(bank: Bank): BankAdapter {
        return when (bank) {
            Bank.RB -> rbBankAdapter
            Bank.CSAS -> csasBankAdapter
        }
    }
}