package cz.vatras.openbanking.infrastructure.bank.csas
import cz.vatras.openbanking.domain.account.Account
import cz.vatras.openbanking.domain.balance.Balance
import cz.vatras.openbanking.domain.transaction.Transaction
import cz.vatras.openbanking.infrastructure.bank.BankAdapter
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasAccountMapper
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasBalanceMapper
import cz.vatras.openbanking.infrastructure.bank.csas.mapper.CsasTransactionMapper
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountRelationship
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountListItem
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAccountServicer
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAgent
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasPartyAccountIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasAmountDetails
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalance
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceDate
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceResponse
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBalanceType
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasBankTransactionCode
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCharges
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCodeOrProprietary
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCounterValueAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCreditorReferenceInformation
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasCurrencyExchange
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasDate
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasEntryDetails
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasFinancialInstitutionIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasIdentification
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasInstructedAmount
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasOther
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasParty
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasProprietary
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasProprietaryParty
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasReferences
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasRemittanceInformation
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasRelatedAgents
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasRelatedParties
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasStructured
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransaction
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransactionDetails
import cz.vatras.openbanking.infrastructure.bank.csas.model.CsasTransactionResponse
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CsasBankAdapter(
    private val csasAccountMapper: CsasAccountMapper,
    private val csasBalanceMapper: CsasBalanceMapper,
    private val csasTransactionMapper: CsasTransactionMapper
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

    override suspend fun getTransactions(account: Account): List<Transaction> {
        val csasTransactionResponse = CsasTransactionResponse(
            pageNumber = 0,
            pageCount = 2,
            pageSize = 10,
            nextPage = 1,
            transactions = listOf(
                CsasTransaction(
                    entryReference = "FC-4567513951",
                    reservationId = "498765432",
                    amount = CsasAmount(
                        value = BigDecimal("4520.15"),
                        currency = "CZK"
                    ),
                    creditDebitIndicator = "CRDT",
                    status = "BOOK",
                    bookingDate = CsasDate(
                        date = "2019-10-20"
                    ),
                    valueDate = CsasDate(
                        date = "2019-10-20"
                    ),
                    bankTransactionCode = CsasBankTransactionCode(
                        proprietary = CsasProprietary(
                            code = 10000202000,
                            issuer = "Czech Banking Association"
                        )
                    ),
                    entryDetails = CsasEntryDetails(
                        transactionDetails = CsasTransactionDetails(
                            references = CsasReferences(
                                accountServicerReference = "0622510833568",
                                endToEndIdentification = "VS0250117002/SS0000000000/KS0000",
                                chequeNumber = "451161XXXXXX2638"
                            ),
                            amountDetails = CsasAmountDetails(
                                instructedAmount = CsasInstructedAmount(
                                    amount = CsasAmount(
                                        value = BigDecimal("4520.15"),
                                        currency = "CZK"
                                    )
                                ),
                                counterValueAmount = CsasCounterValueAmount(
                                    amount = CsasAmount(
                                        value = BigDecimal("4520.15"),
                                        currency = "CZK"
                                    ),
                                    currencyExchange = CsasCurrencyExchange(
                                        sourceCurrency = "GBP",
                                        targetCurrency = "CZK",
                                        exchangeRate = BigDecimal("35.525")
                                    )
                                )
                            ),
                            charges = CsasCharges(
                                bearer = "OUR"
                            ),
                            relatedParties = CsasRelatedParties(
                                debtor = CsasParty(
                                    name = "Hlobil Ferdinand"
                                ),
                                debtorAccount = CsasPartyAccountIdentification(
                                    identification = CsasIdentification(
                                        iban = "CZ0827000000002108589434",
                                        other = CsasOther(
                                            identification = "000000-2108589434/0800"
                                        )
                                    )
                                ),
                                creditor = CsasParty(
                                    name = "Hlobil Ferdinand"
                                ),
                                creditorAccount = CsasPartyAccountIdentification(
                                    identification = CsasIdentification(
                                        iban = "CZ0827000000002108589434",
                                        other = CsasOther(
                                            identification = "000000-2108589434/0800"
                                        )
                                    )
                                ),
                                proprietary = CsasProprietaryParty(
                                    party = CsasParty(
                                        name = "ČS, nám. Arnošta z Pardub, Český Brod CZ"
                                    )
                                )
                            ),
                            relatedAgents = CsasRelatedAgents(
                                creditorAgent = CsasAgent(
                                    financialInstitutionIdentification = CsasFinancialInstitutionIdentification(
                                        bic = "GIBACZPXXXX"
                                    )
                                ),
                                debtorAgent = CsasAgent(
                                    financialInstitutionIdentification = CsasFinancialInstitutionIdentification(
                                        bic = "GIBACZPXXXX"
                                    )
                                )
                            ),
                            remittanceInformation = CsasRemittanceInformation(
                                unstructured = "faktura 12345",
                                structured = CsasStructured(
                                    creditorReferenceInformation = CsasCreditorReferenceInformation(
                                        reference = listOf(
                                            "VS:123456",
                                            "SS:879213546",
                                            "KS:456789"
                                        )
                                    )
                                )
                            ),
                            additionalTransactionInformation = "Tuzemská odchozí úhrada",
                            additionalRemittanceInformation = "na motorku",
                            additionalTransactionDescription = "SEPA PŘEVOD"
                        )
                    )
                )
            )
        )
        return csasTransactionMapper.mapToTransactions(csasTransactionResponse)
    }
}