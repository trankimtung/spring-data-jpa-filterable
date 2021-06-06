package com.trankimtung.spring.data.jpa.filterable.util

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
         * Determines given type contains the specified member or path.
         *
         * @param type target class.
         * @param name member name or path.
         * @return true if the class contains exact match for given member/path.
         */
        internal fun <T : Any> containsMember(type: KClass<T>, name: String): Boolean {
            return if (name.contains(LABEL_DOT)) {
                val firstMember =
                    type.members.firstOrNull { it.name == name.substringBefore(LABEL_DOT) }
                if (firstMember != null) {
                    containsMember(
                        firstMember.returnType.jvmErasure,
                        name.substringAfter(LABEL_DOT)
                    )
                } else {
                    false
                }
            } else {
                type.members.any { member -> member.name == name }
            }
        }
    }
}