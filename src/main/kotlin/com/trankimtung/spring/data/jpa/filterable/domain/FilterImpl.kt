package com.trankimtung.spring.data.jpa.filterable.domain

/**
 * Default implementation of [Filter].
 */
class FilterImpl(
    override val name: String,
    override val operator: Operator,
    override val value: Any?,
    override val values: List<Any>?
) : Filter