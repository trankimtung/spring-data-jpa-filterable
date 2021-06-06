package com.trankimtung.spring.data.jpa.filterable.test

import com.trankimtung.spring.data.jpa.filterable.repository.FilterableExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface TestEntityRepo : FilterableExecutor<TestEntity>, JpaRepository<TestEntity, Int>