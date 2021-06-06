package com.trankimtung.spring.data.jpa.filterable.util

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ReflectsTest {

    @Test
    fun `containsMember() - single level, target member exists`() {
        assertTrue(Reflects.containsMember(ExampleClass::class, "top"))
    }

    @Test
    fun `containsMember() - single level, target member does not exist`() {
        assertFalse(Reflects.containsMember(ExampleClass::class, "nothing"))
    }

    @Test
    fun `containsMember() - multiple level, target member exists`() {
        assertTrue(Reflects.containsMember(ExampleClass::class, "lower.leaf"))
    }

    @Test
    fun `containsMember() - multiple level, target member does not exist`() {
        assertFalse(Reflects.containsMember(ExampleClass::class, "lower.something.leaf"))
    }

    @Suppress("unused")
    private class ExampleClass {
        var top: String? = null
        var lower: InsideClass? = null

        class InsideClass {
            var leaf: String? = null
        }
    }
}