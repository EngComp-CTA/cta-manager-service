package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.domain.exceptions.BaseException
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

sealed class ApiException(msg: String, val code: Int) : Exception(msg)

class NotFoundException(
    msg: String,
    code: Int = HttpStatus.NOT_FOUND.value()
) : ApiException(msg, code)

@ControllerAdvice
class DefaultExceptionHandler {
    @ExceptionHandler(value = [ApiException::class])
    fun onApiException(ex: ApiException, response: HttpServletResponse): Unit =
        response.sendError(ex.code, ex.message)

    @ExceptionHandler(value = [BaseException::class])
    fun onBaseException(ex: BaseException, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.mensagem)

    @ExceptionHandler(value = [NaoEncontradoException::class])
    fun onBaseException(ex: NaoEncontradoException, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.mensagem)

    @ExceptionHandler(value = [NotImplementedError::class])
    fun onNotImplemented(ex: NotImplementedError, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.NOT_IMPLEMENTED.value())

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun onConstraintViolation(ex: ConstraintViolationException, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.constraintViolations.joinToString(", ") { it.message })
}
