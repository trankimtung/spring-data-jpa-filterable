package com.trankimtung.spring.data.jpa.filterable.test

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "filterable_test_table")
class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var string: String = ""
    var int: Int = 0
    var double: Double = 0.0
    var enum: TestEnum = TestEnum.VALUE1
}