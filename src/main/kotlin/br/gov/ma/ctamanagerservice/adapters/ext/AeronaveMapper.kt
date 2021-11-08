package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.domain.entities.*

fun Aeronave.mapToDto(): AeronaveDto {
    return AeronaveDto(
        id = id,
        numeroSerie = numeroSerie,
        modelo = modelo,
        fabricante = fabricante.toString(),
        marcas = marcas.toString()
    )
}

fun AeronaveDto.mapToDomain(): Aeronave {
    return Aeronave(
        id = 0,
        numeroSerie = numeroSerie!!,
        modelo = modelo!!,
        fabricante = Fabricante(id = 1, descricao = "helio"),
        marcas = Marcas(
            marcaMatricula = MarcaMatricula(descricao = marcas),
            marcaNacionalidade = MarcaNacionalidade.PP
        )
    )
}