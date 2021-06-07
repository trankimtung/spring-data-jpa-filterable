package com.trankimtung.spring.data.jpa.filterable.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Default implementation of [Order].
 */
class OrderImpl @JsonCreator constructor(
    @JsonProperty("name") override val name: String,
    @JsonProperty("direction") override val direction: String?
) : Order