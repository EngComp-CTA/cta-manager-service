package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.adapters.api.controllers.mapToDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.MarcaNacionalidade
import br.gov.ma.ctamanagerservice.domain.entities.Marcas


fun Aeronave.mapToDto(): AeronaveDto {
    return AeronaveDto(
        id = id,
        numeroSerie = numeroSerie,
        modelo = modelo,
        fabricante = fabricante.mapToDto(),
        marcas = marcas.toString(),
        apelido = apelido,
        categoriaRegistro = categoria.toString()
    )
}

fun AeronaveDto.mapToDomain(): Aeronave {
    return Aeronave(
        id = 0,
        apelido = "",
        categoria = CategoriaRegistro.valueOf(categoriaRegistro.uppercase()),
        marcas = Marcas(
            marcaMatricula = marcas,
            marcaNacionalidade = MarcaNacionalidade.PP
        ),
        fabricante = Fabricante(id = 1, nome = "helio"),
        modelo = modelo,
        numeroSerie = numeroSerie
    )
}