package com.nedaluof.qurany.data.model

/**
 * Created by nedaluof on 12/11/2020.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
                Resource(status = Status.ERROR, data = data, message = message)

        /*fun <T> loading(data: T?): Resource<T> = Resource(status = Status.LOADING, data = data, message = null)*/
    }
}
enum class Status {
    SUCCESS,
    ERROR,
    //LOADING
}

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
