package com.trankimtung.spring.data.jpa.filterable.domain

import org.springframework.data.domain.Pageable

/**
 * Interface for pagination and filter information.
 */
interface FilterablePageable : Pageable {

    /**
     * list of [Filter], must not be null.
     */
    val filters: List<Filter>
}