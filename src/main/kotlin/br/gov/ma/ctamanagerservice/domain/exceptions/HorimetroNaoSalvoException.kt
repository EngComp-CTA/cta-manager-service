package br.gov.ma.ctamanagerservice.domain.exceptions

data class HorimetroNaoSalvoException(
    override val tipo: String = "GATEWAY_ERROR",
    override val mensagem: String = "Houve um erro ao salvar o horimetro"
) : BaseException()
