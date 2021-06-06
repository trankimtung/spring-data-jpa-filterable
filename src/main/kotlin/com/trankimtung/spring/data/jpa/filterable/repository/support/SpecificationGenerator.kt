package com.trankimtung.spring.data.jpa.filterable.repository.support

import com.trankimtung.spring.data.jpa.filterable.domain.Filter
import com.trankimtung.spring.data.jpa.filterable.domain.Operator
import com.trankimtung.spring.data.jpa.filterable.domain.asString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import kotlin.reflect.KClass

/**
 * [Specification] generator for [Filter].
 */
@Suppress("UNCHECKED_CAST")
class SpecificationGenerator {

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(SpecificationGenerator::class.java)
        private const val SQL_LIKE_PATTERN = "%%%s%%"

        /**
         * For calling from Java.
         *
         * Generates [Specification] from given list of [Filter].
         *
         * @param filters must not be null.
         * @param type entity's class, must not be null.
         * @param relaxed if true, ignores a filter when [Filter.name] is not an attribute of [type]. Must not be null.
         * @return never null.
         */
        @JvmStatic
        fun <T : Any> with(filters: List<Filter>, type: Class<T>, relaxed: Boolean = true): Specification<T> =
            with(filters, type.kotlin, relaxed)

        /**
         * Generates [Specification] from given list of [Filter].
         *
         * @param filters must not be null.
         * @param type entity's class, must not be null.
         * @param relaxed if true, ignores a filter when [Filter.name] is not an attribute of [type]. Must not be null.
         * @return never null.
         */
        @JvmStatic
        fun <T : Any> with(filters: List<Filter>, type: KClass<T>, relaxed: Boolean = true): Specification<T> {
            logger.debug("Generating specification, relaxed = {}.", relaxed)
            var relaxedFilters = filters
            if (relaxed) {
                relaxedFilters = filters.filter { filter ->
                    type.members.any { member -> member.name == filter.name }
                }
            }
            return with(relaxedFilters)
        }

        /**
         * Generates [Specification] from given list of [Filter].
         *
         * @param filters must not be null.
         * @return never null.
         */
        @JvmStatic
        fun <T : Any> with(filters: List<Filter>): Specification<T> {
            return Specification { root, _, criteriaBuilder ->
                val predicates = filters.map { filter ->
                    logger.debug("Filter: {}", filter.asString())
                    when (filter.operator) {
                        Operator.EQUAL -> equalPredicate(filter, root, criteriaBuilder)
                        Operator.NOT -> notPredicate(filter, root, criteriaBuilder)
                        Operator.LIKE -> likePredicate(filter, root, criteriaBuilder)
                        Operator.NOT_LIKE -> notLikePredicate(filter, root, criteriaBuilder)
                        Operator.IN -> inPredicate(filter, root)
                        Operator.NOT_IN -> notInPredicate(filter, root)
                        Operator.GREATER -> greaterPredicate(filter, root, criteriaBuilder)
                        Operator.GREATER_OR_EQUAL -> greaterOrEqualPredicate(filter, root, criteriaBuilder)
                        Operator.LESS -> lessPredicate(filter, root, criteriaBuilder)
                        Operator.LESS_OR_EQUAL -> lessOrEqualPredicate(filter, root, criteriaBuilder)
                    }
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        private fun <T : Any> equalPredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.equal(root.get<Any>(filter.name), filter.value)
        }

        private fun <T : Any> notPredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.notEqual(root.get<Any>(filter.name), filter.value)
        }

        private fun <T : Any> likePredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.like(root.get(filter.name), String.format(SQL_LIKE_PATTERN, filter.value))
        }

        private fun <T : Any> notLikePredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.notLike(root.get(filter.name), String.format(SQL_LIKE_PATTERN, filter.value))
        }

        private fun <T : Any> inPredicate(
            filter: Filter,
            root: Root<T>
        ): Predicate {
            return root.get<Any>(filter.name).`in`(filter.values)
        }

        private fun <T : Any> notInPredicate(
            filter: Filter,
            root: Root<T>
        ): Predicate {
            return root.get<Any>(filter.name).`in`(filter.values).not()
        }

        private fun <T : Any> greaterPredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.greaterThan(root.get(filter.name), filter.value as Comparable<Any>)
        }

        private fun <T : Any> greaterOrEqualPredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.name), filter.value as Comparable<Any>)
        }

        private fun <T : Any> lessPredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.lessThan(root.get(filter.name), filter.value as Comparable<Any>)
        }

        private fun <T : Any> lessOrEqualPredicate(
            filter: Filter,
            root: Root<T>,
            criteriaBuilder: CriteriaBuilder
        ): Predicate {
            return criteriaBuilder.lessThanOrEqualTo(root.get(filter.name), filter.value as Comparable<Any>)
        }
    }
}