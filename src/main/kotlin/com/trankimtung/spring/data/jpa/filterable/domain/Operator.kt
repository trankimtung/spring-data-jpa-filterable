package com.trankimtung.spring.data.jpa.filterable.domain

enum class Operator(
    /**
     * Short symbol representing the operator's meaning.
     */
    val symbol: String
) {
    EQUAL("="),
    NOT("!="),
    LIKE("~"),
    NOT_LIKE("!~"),
    IN("in"),
    NOT_IN("!in"),
    GREATER(">"),
    GREATER_OR_EQUAL(">="),
    LESS("<"),
    LESS_OR_EQUAL("<=");

    companion object {

        /**
         * Gets the [Operator] value by its short symbol.
         *
         * @param symbol the operator's short symbol.
         * @return the equivalent [Operator].
         * @throws NoSuchElementException if the given symbol is not recognized.
         */
        @JvmStatic
        fun fromSymbol(symbol: String): Operator {
            return values().first { it.symbol == symbol }
        }
    }
}