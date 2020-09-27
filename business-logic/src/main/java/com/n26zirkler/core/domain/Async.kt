package com.n26zirkler.core.domain

import java.util.Arrays

/**
 * Async makes dealing with async requests like fetching from a network, database, or anything that
 * can be mapped to an observable easy.
 * Async is a sealed class with four subclasses: Loading, Success, Fail.
 * Taken from https://github.com/airbnb/MvRx/wiki#asynct
 */
sealed class Async<out T>(private val value: T?) {
    open operator fun invoke(): T? = value
}

data class Loading<out T>(private val value: T? = null) : Async<T>(value = value)

data class Success<out T>(private val value: T) : Async<T>(value = value) {
    override operator fun invoke(): T = value
}

data class Fail<out T>(val error: Throwable, private val value: T? = null) : Async<T>(value = value) {
    override fun equals(other: Any?): Boolean {
        if (other !is Fail<*>) return false

        val otherError = other.error
        return error::class == otherError::class &&
            error.message == otherError.message &&
            error.stackTrace.firstOrNull() == otherError.stackTrace.firstOrNull()
    }

    override fun hashCode(): Int = Arrays.hashCode(arrayOf(error::class, error.message, error.stackTrace.firstOrNull()))
}
