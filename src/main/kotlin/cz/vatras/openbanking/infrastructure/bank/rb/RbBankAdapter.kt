package cz.vatras.openbanking.infrastructure.bank.rb
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.balance.Balance
import cz.vatras.openbanking.domain.transaction.Transaction
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.rb.mapper.RbAccountMapper
import cz.vatras.openbanking.infrastructure.bank.rb.mapper.RbBalanceMapper
import cz.vatras.openbanking.infrastructure.bank.rb.mapper.RbTransactionMapper
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccountListItem
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAmount
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbBankTransactionCode
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbBalanceResponse
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCounterParty
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCurrencyBalance
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCurrencyFolder
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbCreditorReferenceInformation
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbEntryDetails
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbInstructedAmount
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbIntermediaryInstitution
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbOrganisationIdentification
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbPostalAddress
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbReferences
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbRemittanceInformation
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbRelatedParties
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransaction
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransactionDetails
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbTransactionResponse
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbUltimateCounterParty
import cz.vatras.openbanking.infrastructure.bank.rb.model.RbAccount
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class RbBankAdapter(
    private val rbAccountMapper: RbAccountMapper,
    private val rbBalanceMapper: RbBalanceMapper,
    private val rbTransactionMapper: RbTransactionMapper
) : BankAdapter {
    override suspend fun getAccounts(): List<Account> {
        val rbAccountListItems = listOf(
            RbAccountListItem(
                accountId = "123456789",
                accountName = "Personal Account",
                friendlyName = "My Main Account",
                accountNumber = "123456789",
                accountNumberPrefix = "0000",
                iban = "CZ6508000000001234567890",
                bankCode = "0800",
                bankBicCode = "CZKOCZ1X",
                mainCurrency = "CZK",
                accountTypeId = "CURRENT"
            ),
            RbAccountListItem(
                accountId = "987654321",
                accountName = "Savings Account",
                friendlyName = "My Savings",
                accountNumber = "987654321",
                accountNumberPrefix = "0000",
                iban = "CZ6508000000009876543210",
                bankCode = "0800",
                bankBicCode = "CZKOCZ1X",
                mainCurrency = "CZK",
                accountTypeId = "SAVINGS"
            )
        )
        return rbAccountMapper.mapToAccounts(rbAccountListItems)
    }

    override suspend fun getBalances(account: Account): List<Balance> {
        val rbBalanceResponse = RbBalanceResponse(
            numberPart1 = "19",
            numberPart2 = "6760653036",
            bankCode = "5500",
            currencyFolders = listOf(
                RbCurrencyFolder(
                    currency = "CZK",
                    status = "ACTIVE",
                    balances = listOf(
                        RbCurrencyBalance(
                            balanceType = "CLAB",
                            currency = "CZK",
                            value = BigDecimal("1000.5")
                        )
                    )
                ),
                RbCurrencyFolder(
                    currency = "EUR",
                    status = "ACTIVE",
                    balances = listOf(
                        RbCurrencyBalance(
                            balanceType = "CLAB",
                            currency = "EUR",
                            value = BigDecimal("500.25")
                        )
                    )
                )
            )
        )
        return rbBalanceMapper.mapToBalances(rbBalanceResponse)
    }

    override suspend fun getTransactions(account: Account): List<Transaction> {
        val rbTransactionResponse = RbTransactionResponse(
            lastPage = true,
            transactions = listOf(
                RbTransaction(
                    entryReference = "3887196517",
                    amount = RbAmount(
                        value = BigDecimal("-1668.59"),
                        currency = "CZK"
                    ),
                    creditDebitIndication = "DBIT",
                    bookingDate = "2026-09-13T10:01:23.615Z",
                    valueDate = "2026-09-13T10:01:23.615Z",
                    bankTransactionCode = RbBankTransactionCode(
                        code = "10000401000"
                    ),
                    entryDetails = RbEntryDetails(
                        transactionDetails = RbTransactionDetails(
                            references = RbReferences(
                                endToEndIdentification = "EBGTF015477198"
                            ),
                            instructedAmount = RbInstructedAmount(
                                value = BigDecimal("61"),
                                currency = "EUR",
                                exchangeRate = BigDecimal("0.03656")
                            ),
                            chargeBearer = "SHAR",
                            paymentCardNumber = "547872XXXXXX9475",
                            relatedParties = RbRelatedParties(
                                counterParty = RbCounterParty(
                                    name = "Firma Ltd.",
                                    postalAddress = RbPostalAddress(
                                        street = "Na dlouhém lánu 35",
                                        city = "Praha 6",
                                        country = "CZ"
                                    ),
                                    organisationIdentification = RbOrganisationIdentification(
                                        name = "Firma Ltd.",
                                        bicOrBei = "KOMBCZPP",
                                        bankCode = "0100",
                                        postalAddress = RbPostalAddress(
                                            street = "Na dlouhém lánu 35",
                                            city = "Praha 6",
                                            country = "CZ"
                                        )
                                    ),
                                    account = RbAccount(
                                        iban = "CZ0801000000192000145399",
                                        accountNumberPrefix = "000019",
                                        accountNumber = "2000145399"
                                    )
                                ),
                                intermediaryInstitution = RbIntermediaryInstitution(
                                    name = "Komerční banka, a.s.",
                                    bicOrBei = "KOMBCZPP",
                                    bankCode = "0100",
                                    postalAddress = RbPostalAddress(
                                        street = "Na příkopě 969/33",
                                        city = "Praha 1",
                                        country = "CZ"
                                    )
                                ),
                                ultimateCounterParty = RbUltimateCounterParty(
                                    name = "CZ0801000000192000145399",
                                    postalAddress = RbPostalAddress(
                                        street = "Na dlouhém lánu 35",
                                        city = "Praha 6",
                                        country = "CZ"
                                    )
                                )
                            ),
                            remittanceInformation = RbRemittanceInformation(
                                unstructured = "61 EUR;FIRMA LTD;12345678 RADEK DVA RADEK TRI RADEK CTYRI",
                                creditorReferenceInformation = RbCreditorReferenceInformation(
                                    variable = "1234567890",
                                    constant = "558",
                                    specific = "1234567890"
                                ),
                                originatorMessage = "naše platba"
                            )
                        )
                    )
                )
            )
        )
        return rbTransactionMapper.mapToTransactions(rbTransactionResponse)
    }
}