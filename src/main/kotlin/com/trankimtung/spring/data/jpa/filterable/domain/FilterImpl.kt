package com.trankimtung.spring.data.jpa.filterable.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Default implementation of [Filter].
 */
class FilterImpl @JsonCreator constructor(
    @JsonProperty("name") override val name: String,
    @JsonProperty("operator") override val operator: Operator,
    @JsonProperty("value") override val value: Any?,
    @JsonProperty("values") override val values: List<Any?>?
) : Filter