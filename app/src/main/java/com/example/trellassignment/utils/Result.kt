package com.example.trellassignment.utils

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String): Result<T> {
            return Result(
                Status.ERROR,
                null,
                message
            )
        }

        fun <T> loading(): Result<T> {
            return Result(
                Status.LOADING,
                null,
                null
            )
        }
    }
}