package com.trankimtung.spring.data.jpa.filterable.repository.support

import com.trankimtung.spring.data.jpa.filterable.domain.Filter
import com.trankimtung.spring.data.jpa.filterable.domain.Operator
import com.trankimtung.spring.data.jpa.filterable.test.TestEntity
import com.trankimtung.spring.data.jpa.filterable.test.TestEntityRepo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessApiUsageException

@SpringBootTest
internal class SpecificationGeneratorTest @Autowired constructor(
    private val testEntityRepo: TestEntityRepo
) {

    @Test
    fun `Includes all filters by default`() {
        val filters = listOf(
            object : Filter {
                override val name: String = "id"
                override val operator: Operator = Operator.EQUAL
                override val value: Any? = null
                override val values: List<Any>? = null
            },
            object : Filter {
                override val name: String = "string"
                override val operator: Operator = Operator.EQUAL
                override val value: Any? = null
                override val values: List<Any>? = null
            }
        )
        val spec = SpecificationGenerator.with<TestEntity>(filters)
        assertDoesNotThrow {
            testEntityRepo.findAll(spec)
        }
    }

    @Test
    fun `Throws when a filter's name is not a member of given type`() {
        val filters = listOf(
            object : Filter {
                override val name: String = "id"
                override val operator: Operator = Operator.EQUAL
                override val value: Any? = null
                override val values: List<Any>? = null
            },
            object : Filter {
                override val name: String = "something"
                override val operator: Operator = Operator.EQUAL
                override val value: Any? = null
                override val values: List<Any>? = null
            }
        )
        val spec = SpecificationGenerator.with<TestEntity>(filters)
        assertThrows<InvalidDataAccessApiUsageException> {
            testEntityRepo.findAll(spec)
        }
    }

    @Test
    fun `If relaxes is true, ignores a filter when its name is not a member of given type`() {
        val filters = listOf(
            object : Filter {
                override val name: String = "id"
                override val operator: Operator = Operator.EQUAL
                override val value: Any? = null
                override val values: List<Any>? = null
            },
            object : Filter {
                override val name: String = "something"
                override val operator: Operator = Operator.EQUAL
                override val value: Any? = null
                override val values: List<Any>? = null
            }
        )
        val spec = SpecificationGenerator.with(filters, TestEntity::class.java, true)
        assertDoesNotThrow {
            testEntityRepo.findAll(spec)
        }
    }

    @Test
    fun `Type incompatible`() {
        val filters = listOf(
            object : Filter {
                override val name: String = "int"
                override val operator: Operator = Operator.EQUAL
                override val value: Double = 1.0
                override val values: List<Double>? = null
            }
        )
        val spec = SpecificationGenerator.with(filters, TestEntity::class.java, true)
        assertDoesNotThrow {
            testEntityRepo.findAll(spec)
        }
    }
}