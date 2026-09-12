package cz.vatras.openbanking.application

enum class Bank(val code: String) {
    RB("RB"),
    CSAS("CSAS");

    companion object {
        fun fromCode(code: String?): Bank? {
            return entries.find { it.code == code }
        }
    }
}