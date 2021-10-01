package br.gov.ma.ctamanagerservice.domain.entities

data class Aeronave(
    val id: Long,
    val marcas: Marcas,
    val fabricante: Fabricante,
    val modelo: String,
    val numeroSerie: String
)