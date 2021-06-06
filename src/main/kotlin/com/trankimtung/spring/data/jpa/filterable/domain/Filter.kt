@file:JvmName("FilterUtils")

package com.trankimtung.spring.data.jpa.filterable.domain

/**
 * Interface for filter information.
 */
interface Filter {

    /**
     * Name or path to the member used for the filter.
     */
    val name: String

    /**
     * Desired [Operator].
     */
    val operator: Operator

    /**
     * Desired value. Not applicable for [Operator.IN] and [Operator.NOT_IN].
     */
    val value: Any?

    /**
     * Desired values. Only applicable for [Operator.IN] and [Operator.NOT_IN].
     */
    val values: List<Any>?
}

/**
 * Gets a string representation of [Filter].
 */
fun Filter.asString(): String {
    return "Filter(name='$name', operator=$operator, value=$value, values=$values)"
}