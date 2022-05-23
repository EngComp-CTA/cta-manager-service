package br.gov.ma.ctamanagerservice.domain.exceptions

abstract class BaseException : Exception() {
    abstract val tipo: String
    abstract val mensagem: String
}
