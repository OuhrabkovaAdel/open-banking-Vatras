package cz.vatras.openbanking.api.converter

import cz.vatras.openbanking.application.Bank
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class BankConverter : Converter<String, Bank> {
    override fun convert(bank: String): Bank {
        return Bank.fromCode(bank)
            ?: throw IllegalArgumentException("Invalid bank code: $bank")
    }
}