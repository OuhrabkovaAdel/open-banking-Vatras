package cz.vatras.openbanking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenBankingApplication

fun main(args: Array<String>) {
	runApplication<OpenBankingApplication>(*args)
}
