package br.gov.ma.ctamanagerservice.domain.entities

data class Pessoa(
    val id: Long,
    val nome: String,
    val cpf: String,
    val telefone: String?
)
