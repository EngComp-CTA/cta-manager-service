package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.adapters.api.controllers.mapToDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.*


fun Aeronave.mapToDto(): AeronaveDto {
    return AeronaveDto(
        id = id,
        numeroSerie = numeroSerie,
        modelo = modelo,
        fabricante = fabricante.mapToDto(),
        marcas = marcas.toString(),
        apelido = apelido,
        categoriaRegistro = CategoriaRegistro.valueOf(categoria)
    )
}

fun AeronaveDto.mapToDomain(): Aeronave {
    return Aeronave(
        id = 0,
        apelido = "",
        categoria = "",
        marcas = Marcas(
            marcaMatricula = marcas,
            marcaNacionalidade = MarcaNacionalidade.PP
        ),
        fabricante = Fabricante(id = 1, nome = "helio"),
        modelo = modelo!!,
        numeroSerie = numeroSerie!!.toInt(),
    )
}