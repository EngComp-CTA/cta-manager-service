package br.gov.ma.ctamanagerservice.domain.entities

data class Aeronauta(
    val id: Long,
    val pessoa: Pessoa,
    val codigoAnac: Int
)
