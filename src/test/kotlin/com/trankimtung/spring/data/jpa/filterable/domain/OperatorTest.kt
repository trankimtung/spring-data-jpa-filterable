package com.trankimtung.spring.data.jpa.filterable.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class OperatorTest {

    @Test
    fun `All symbols are correct`() {
        assertEquals("=", Operator.EQUAL.symbol)
        assertEquals("!=", Operator.NOT.symbol)
        assertEquals("~", Operator.LIKE.symbol)
        assertEquals("!~", Operator.NOT_LIKE.symbol)
        assertEquals("in", Operator.IN.symbol)
        assertEquals("!in", Operator.NOT_IN.symbol)
        assertEquals(">", Operator.GREATER.symbol)
        assertEquals(">=", Operator.GREATER_OR_EQUAL.symbol)
        assertEquals("<", Operator.LESS.symbol)
        assertEquals("<=", Operator.LESS_OR_EQUAL.symbol)
    }

    @Test
    fun `Getting correct operator by symbol`() {
        assertEquals(Operator.EQUAL, Operator.fromSymbol(Operator.EQUAL.symbol))
        assertEquals(Operator.NOT, Operator.fromSymbol(Operator.NOT.symbol))
        assertEquals(Operator.LIKE, Operator.fromSymbol(Operator.LIKE.symbol))
        assertEquals(Operator.NOT_LIKE, Operator.fromSymbol(Operator.NOT_LIKE.symbol))
        assertEquals(Operator.IN, Operator.fromSymbol(Operator.IN.symbol))
        assertEquals(Operator.NOT_IN, Operator.fromSymbol(Operator.NOT_IN.symbol))
        assertEquals(Operator.GREATER, Operator.fromSymbol(Operator.GREATER.symbol))
        assertEquals(Operator.GREATER_OR_EQUAL, Operator.fromSymbol(Operator.GREATER_OR_EQUAL.symbol))
        assertEquals(Operator.LESS, Operator.fromSymbol(Operator.LESS.symbol))
        assertEquals(Operator.LESS_OR_EQUAL, Operator.fromSymbol(Operator.LESS_OR_EQUAL.symbol))
    }

    @Test
    fun `Throws when given symbol is not recognized`() {
        assertThrows(NoSuchElementException::class.java) {
            Operator.fromSymbol("@")
        }
    }
}