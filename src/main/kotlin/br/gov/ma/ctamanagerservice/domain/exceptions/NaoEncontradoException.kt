package br.gov.ma.ctamanagerservice.domain.exceptions

class NaoEncontradoException(
    val tipo: String = "NOT_FOUND",
    val mensagem: String
) : Exception()
