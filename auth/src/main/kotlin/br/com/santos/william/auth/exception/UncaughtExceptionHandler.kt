package br.com.santos.william.auth.exception

import br.com.santos.william.auth.message.MessageSchedule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.validation.ConstraintViolationException


@ControllerAdvice
class UncaughtExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(UncaughtExceptionHandler::class.java)

    @ExceptionHandler(java.lang.Exception::class)
    fun anyException(exception: Exception): ResponseEntity<Any> {
        logger.error("UncaughtException", exception)
        return ResponseEntity(mapOf<Any, Any>(
                "error_code" to "Unexpected error has occurred",
                "description" to "Internal server error"
        ), HttpStatus.INTERNAL_SERVER_ERROR)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(exception: ConstraintViolationException): ResponseEntity<Any?>? {
        logger.error("Suppress constraint violation log, enable debug mode to show them")
        logger.debug("Constraint violation", exception)
        val errors: MutableList<String> = ArrayList()
        for (violation in exception.constraintViolations) {
            errors.add(violation.propertyPath.toString() + ": " + violation.message)
        }
        return ResponseEntity(mapOf<Any, Any>(
                "error_code" to "bad_request",
                "errors" to errors
        ), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<Any?>? {
        logger.error("Suppress constraint violation log, enable debug mode to show them")
        logger.debug("Constraint violation", exception)
        val errors: MutableList<String> = ArrayList()
        for (violation in exception.bindingResult.fieldErrors ) {
            errors.add(violation.field + ": " + violation.defaultMessage)
        }
        return ResponseEntity(mapOf<Any, Any>(
                "error_code" to "bad_request",
                "errors" to errors
        ), HttpStatus.BAD_REQUEST)
    }

}