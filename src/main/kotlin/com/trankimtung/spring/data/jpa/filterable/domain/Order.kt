package com.trankimtung.spring.data.jpa.filterable.domain

import org.springframework.data.domain.Sort

/**
 * Interface for ordering information.
 */
interface Order {

    /**
     * Name or path to the member used for ordering.
     */
    val name: String

    /**
     * Desired ordering direction.
     */
    val direction: String?
}

/**
 * gets Spring [Sort.Order] representation.
 */
fun Order.asSpringOrder(): Sort.Order {
    return Sort.Order(Sort.Direction.fromString(direction ?: "asc"), name)
}