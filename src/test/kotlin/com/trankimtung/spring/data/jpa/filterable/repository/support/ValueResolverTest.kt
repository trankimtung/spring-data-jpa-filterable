package com.trankimtung.spring.data.jpa.filterable.repository.support

import com.trankimtung.spring.data.jpa.filterable.test.TestEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.*
import java.util.*

internal class ValueResolverTest {

    @Test
    fun string() {
        assertEquals("123.0", resolveValue(123.0, String::class))
        assertEquals("123.0", resolveValue(123.0, CharSequence::class))
    }

    @Test
    fun boolean() {
        assertEquals(true, resolveValue("true", Boolean::class))
        assertEquals(false, resolveValue("false", Boolean::class))
    }

    @Test
    fun number() {
        assertEquals(1.toByte(), resolveValue("1", Byte::class))
        assertEquals(1234.toShort(), resolveValue("1234", Short::class))
        assertEquals(123456, resolveValue("123456", Int::class))
        assertEquals(123456789L, resolveValue("123456789", Long::class))
        assertEquals(123.456.toFloat(), resolveValue(123.456, Float::class))
        assertEquals(123.456789, resolveValue(123.456789, Double::class))
        assertEquals("123.456789".toBigDecimal(), resolveValue(123.456789, BigDecimal::class))
    }

    @Test
    fun enum() {
        assertEquals(TestEnum.VALUE1, resolveValue("VALUE1", TestEnum::class))
        assertEquals(TestEnum.VALUE2, resolveValue("VALUE2", TestEnum::class))
    }

    @Test
    fun `date & time`() {
        assertEquals(
            Date(ZonedDateTime.of(LocalDateTime.of(2021, 1, 1, 0, 0, 0), ZoneId.of("Z")).toInstant().toEpochMilli()),
            resolveValue("2021-01-01T00:00:00+00:00", Date::class)
        )
        assertEquals(LocalDate.of(2021, 1, 1), resolveValue("2021-01-01", LocalDate::class))
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), resolveValue("2021-01-01T00:00:00", LocalDateTime::class))
        assertEquals(
            ZonedDateTime.of(LocalDateTime.of(2021, 1, 1, 0, 0, 0), ZoneId.of("Z")),
            resolveValue("2021-01-01T00:00:00+00:00", ZonedDateTime::class)
        )
        assertEquals(
            ZonedDateTime.of(LocalDateTime.of(2021, 1, 1, 0, 0, 0), ZoneId.of("Z")).toInstant(),
            resolveValue("2021-01-01T00:00:00Z", Instant::class)
        )
    }
}