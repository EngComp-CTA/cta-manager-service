package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto

fun Aeronave.mapToDto(): AeronaveDto {
    return AeronaveDto(
        id = id,
        numeroSerie = numeroSerie,
        modelo = modelo,
        fabricante = fabricante.toString(),
        marcas = marcas.toString()
    )
}