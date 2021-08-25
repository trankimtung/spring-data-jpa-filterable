package com.trankimtung.spring.data.jpa.filterable.repository.support

import com.trankimtung.spring.data.jpa.filterable.util.Reflects
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

fun resolveValue(value: Any?, type: KClass<*>): Any? {
    return when {
        // Direct cast
        value == null -> null
        type::class.isSuperclassOf(value::class) -> value

        // String
        type.isSuperclassOf(String::class) || type.isSuperclassOf(CharSequence::class) -> value.toString()
        type.isSuperclassOf(Char::class) -> value.toString().firstOrNull()

        // Boolean
        type.isSuperclassOf(Boolean::class) -> value.toString().toBoolean()

        // Number
        type.isSuperclassOf(Byte::class) -> value.toString().toBigDecimal().toByte()
        type.isSuperclassOf(Short::class) -> value.toString().toBigDecimal().toShort()
        type.isSuperclassOf(Int::class) -> value.toString().toBigDecimal().toInt()
        type.isSuperclassOf(Long::class) -> value.toString().toBigDecimal().toLong()
        type.isSuperclassOf(Float::class) -> value.toString().toBigDecimal().toFloat()
        type.isSuperclassOf(Double::class) -> value.toString().toBigDecimal().toDouble()
        type.isSuperclassOf(BigDecimal::class) -> value.toString().toBigDecimal()

        // Date & time
        type.isSuperclassOf(Date::class) -> Date(ZonedDateTime.parse(value.toString()).toEpochSecond() * 1000L)
        type.isSuperclassOf(LocalDate::class) -> LocalDate.parse(value.toString())
        type.isSuperclassOf(LocalDateTime::class) -> LocalDateTime.parse(value.toString())
        type.isSuperclassOf(ZonedDateTime::class) -> ZonedDateTime.parse(value.toString())
        type.isSuperclassOf(Instant::class) -> Instant.parse(value.toString())

        // Enum
        type.java.isEnum -> Reflects.getEnumByName(type, value.toString())

        // Other
        else -> value
    }
}

