package com.trankimtung.spring.data.jpa.filterable.repository

import com.trankimtung.spring.data.jpa.filterable.domain.Filter
import com.trankimtung.spring.data.jpa.filterable.domain.FilterablePageable
import com.trankimtung.spring.data.jpa.filterable.repository.support.SpecificationGenerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*
import kotlin.reflect.KClass

/**
 * Interface to allow execution of [Filter] based on [JpaSpecificationExecutor].
 */
@Suppress("unused")
interface FilterableExecutor<T : Any> : JpaSpecificationExecutor<T> {

    /**
     * Returns a [List] of entities matching the given list of [Filter].
     *
     * @param filters must not be null.
     * @param sort must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return list of found entities, never null.
     */
    fun findAll(filters: List<Filter>, sort: Sort, type: Class<T>, relaxed: Boolean = true): List<T> =
        findAll(filters, sort, type, relaxed)

    /**
     * Returns a [List] of entities matching the given list of [Filter].
     *
     * @param filters must not be null.
     * @param sort must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return list of found entities, never null.
     */
    fun findAll(filters: List<Filter>, sort: Sort, type: KClass<T>, relaxed: Boolean = true): List<T> {
        val spec = SpecificationGenerator.with(filters, type, relaxed)
        return findAll(spec, sort)
    }

    /**
     * Returns a [List] of entities matching the given list of [Filter].
     *
     * @param filters must not be null.
     * @param sort must not be null.
     * @return list of found entities, never null.
     */
    fun findAll(filters: List<Filter>, sort: Sort): List<T> {
        val spec = SpecificationGenerator.with<T>(filters)
        return findAll(spec, sort)
    }

    /**
     * For calling from Java.
     *
     * Returns a [Page] of entities matching the given list of [Filter].
     *
     * @param filters must not be null.
     * @param pageable must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return page of found entities, never null.
     */
    fun findAll(filters: List<Filter>, pageable: Pageable, type: Class<T>, relaxed: Boolean = true): Page<T> =
        findAll(filters, pageable, type.kotlin, relaxed)

    /**
     * Returns a [Page] of entities matching the given list of [Filter].
     *
     * @param filters must not be null.
     * @param pageable must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return page of found entities, never null.
     */
    fun findAll(filters: List<Filter>, pageable: Pageable, type: KClass<T>, relaxed: Boolean = true): Page<T> {
        val spec = SpecificationGenerator.with(filters, type, relaxed)
        return findAll(spec, pageable)
    }

    /**
     * Returns a [Page] of entities matching the given list of [Filter].
     *
     * @param filters must not be null.
     * @param pageable must not be null.
     * @return page of found entities, never null.
     */
    fun findAll(filters: List<Filter>, pageable: Pageable): Page<T> {
        val spec = SpecificationGenerator.with<T>(filters)
        return findAll(spec, pageable)
    }

    /**
     * For calling from Java.
     *
     * Returns a [Page] of entities matching the given [FilterablePageable].
     *
     * @param filterable must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return page of found entities, never null.
     */
    fun findAll(filterable: FilterablePageable, type: Class<T>, relaxed: Boolean = true): Page<T> =
        findAll(filterable, type.kotlin, relaxed)

    /**
     * Returns a [Page] of entities matching the given [FilterablePageable].
     *
     * @param filterable must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return page of found entities, never null.
     */
    fun findAll(filterable: FilterablePageable, type: KClass<T>, relaxed: Boolean = true): Page<T> {
        val spec = SpecificationGenerator.with(filterable.filters, type, relaxed)
        return findAll(spec, filterable)
    }

    /**
     * Returns a [Page] of entities matching the given [FilterablePageable].
     *
     * @param filterable must not be null.
     * @return page of found entities, never null.
     */
    fun findAll(filterable: FilterablePageable): Page<T> {
        val spec = SpecificationGenerator.with<T>(filterable.filters)
        return findAll(spec, filterable)
    }

    /**
     * For calling from Java.
     *
     * Returns a single entity matching the given list of [Filter] or [Optional.EMPTY] if none found.
     *
     * @param filters filters, must not be null.
     * @param type entity's Java class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return found entity or [Optional.EMPTY], never null.
     * @throws [org.springframework.dao.IncorrectResultSizeDataAccessException] if more than one entity found.
     */
    fun findOne(filters: List<Filter>, type: Class<T>, relaxed: Boolean = true): Optional<T> =
        findOne(filters, type.kotlin, relaxed)

    /**
     * Returns a single entity matching the given list of [Filter] or [Optional.EMPTY] if none found.
     *
     * @param filters filters, must not be null.
     * @param type entity's class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return found entity or [Optional.EMPTY], never null.
     * @throws [org.springframework.dao.IncorrectResultSizeDataAccessException] if more than one entity found.
     */
    fun findOne(filters: List<Filter>, type: KClass<T>, relaxed: Boolean = true): Optional<T> {
        val spec = SpecificationGenerator.with(filters, type, relaxed)
        return findOne(spec)
    }

    /**
     * Returns a single entity matching the given list of [Filter] or [Optional.EMPTY] if none found.
     *
     * @param filters filters, must not be null.
     * @return found entity or [Optional.EMPTY], never null.
     * @throws [org.springframework.dao.IncorrectResultSizeDataAccessException] if more than one entity found.
     */
    fun findOne(filters: List<Filter>): Optional<T> {
        val spec = SpecificationGenerator.with<T>(filters)
        return findOne(spec)
    }

    /**
     * For calling from Java.
     *
     * Returns the number of entities that the given list of [Filter] will return.
     *
     * @param filters filters, must not be null.
     * @param type entity's class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return number of found entities, never null.
     */
    fun count(filters: List<Filter>, type: Class<T>, relaxed: Boolean): Long =
        count(filters, type.kotlin, relaxed)

    /**
     * Returns the number of entities that the given list of [Filter] will return.
     *
     * @param filters filters, must not be null.
     * @param type entity's class, must not be null.
     * @param relaxed if true, will not throw exception when [Filter.name] is not an attribute of [type]. Must not be null.
     * @return number of found entities, never null.
     */
    fun count(filters: List<Filter>, type: KClass<T>, relaxed: Boolean): Long {
        val spec = SpecificationGenerator.with(filters, type, relaxed)
        return count(spec)
    }

    /**
     * Returns the number of entities that the given list of [Filter] will return.
     *
     * @param filters filters, must not be null.
     * @return number of found entities, never null.
     */
    fun count(filters: List<Filter>): Long {
        val spec = SpecificationGenerator.with<T>(filters)
        return count(spec)
    }
}