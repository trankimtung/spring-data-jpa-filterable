package com.trankimtung.spring.data.jpa.filterable.util

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmErasure

/**
 * Handy reflection utilities.
 */
internal class Reflects {

    companion object {

        @JvmStatic
        private val LABEL_DOT = '.'

        /**
         * Find member with specified name or path.
         *
         * @param type target class.
         * @param name member name or path.
         * @return found member, null if not found.
         */
        internal fun <T : Any> findMember(type: KClass<T>, name: String): KCallable<*>? {
            return if (name.contains(LABEL_DOT)) {
                val member = type.members.find { it.name == name.substringBefore(LABEL_DOT) }
                if (member != null) {
                    findMember(
                        member.returnType.jvmErasure,
                        name.substringAfter(LABEL_DOT)
                    )
                } else {
                    null
                }
            } else {
                type.members.find { member -> member.name == name }
            }
        }

        /**
         * Determines given type contains the specified member or path.
         *
         * @param type target class.
         * @param name member name or path.
         * @return true if the class contains exact match for given member/path.
         */
        internal fun <T : Any> containsMember(type: KClass<T>, name: String): Boolean =
            findMember(type, name) != null

        /**
         *
         */
        internal fun getEnumByName(type: KClass<*>, name: String): Enum<*>? {
            val enums = type.java.enumConstants as? Array<Enum<*>>
            return enums?.first { it.name == name }
        }
    }
}