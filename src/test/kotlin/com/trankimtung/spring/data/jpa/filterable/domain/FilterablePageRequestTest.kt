package com.trankimtung.spring.data.jpa.filterable.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Sort

internal class FilterablePageRequestTest {

    @Test
    fun `Default page 0, size 50, unsorted and no filter`() {
        val pageRequest = FilterablePageRequest()
        assertEquals(0, pageRequest.pageNumber)
        assertEquals(50, pageRequest.pageSize)
        assertEquals(Sort.unsorted(), pageRequest.sort)
        assertTrue(pageRequest.filters.isEmpty())
    }
}