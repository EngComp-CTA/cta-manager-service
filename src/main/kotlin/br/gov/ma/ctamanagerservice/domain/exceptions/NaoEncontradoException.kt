package br.gov.ma.ctamanagerservice.domain.exceptions

data class NaoEncontradoException(
    override val tipo: String = "NOT_FOUND",
    override val mensagem: String
) : BaseException()
