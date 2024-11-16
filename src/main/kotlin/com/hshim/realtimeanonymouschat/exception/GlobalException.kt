package com.hshim.realtimeanonymouschat.exception

import io.autocrypt.sakarinblue.universe.util.ClassUtil.classToMap
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

enum class GlobalException(
    val message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) {

    ;

    val exception = exception(null)
    fun exception(responseBody: Any?): ResponseStatusException {
        val message = responseBody?.classToMap()?.toString() ?: this.message
        return ResponseStatusException(status, message)
    }
}