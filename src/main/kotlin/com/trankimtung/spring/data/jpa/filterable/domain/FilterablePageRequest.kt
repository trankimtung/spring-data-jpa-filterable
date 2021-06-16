package com.trankimtung.spring.data.jpa.filterable.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

/**
 * Default implementation of [FilterablePageable].
 *
 * @param pageable base pageable, must not be null.
 * @param filters list of [Filter], must not be null.
 */
class FilterablePageRequest(
    private val pageable: Pageable,
    override var filters: List<FilterImpl>
) : Pageable by pageable, FilterablePageable {

    /**
     * Creates a new [FilterablePageRequest].
     *
     * @param page zero-based page index, must not be negative. Default 0.
     * @param size the size of the page to be returned, must be greater than 0. Default 50.
     * @param orders order options, default unsorted.
     * @param filters list of [Filter], default empty.
     */
    @JsonCreator
    constructor(
        @JsonProperty("page") page: Int?,
        @JsonProperty("size") size: Int?,
        @JsonProperty("orders") orders: List<OrderImpl>?,
        @JsonProperty("filters") filters: List<FilterImpl>?
    ) : this(
        pageable = PageRequest.of(
            page ?: DEFAULT_PAGE,
            size ?: DEFAULT_SIZE,
            if (orders != null) Sort.by(orders.map { it.asSpringOrder() }) else Sort.unsorted()
        ),
        filters = filters ?: emptyList()
    )

    constructor() : this(null, null, null, null)

    companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_SIZE = 50
    }
}