package com.hyunjung.core.domain.util

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.hyunjung.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}