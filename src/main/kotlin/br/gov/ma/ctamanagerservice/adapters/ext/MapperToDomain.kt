package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveRequestDto
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.dto.HorimetroDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.Marcas

fun AeronaveRequestDto.toDomain() = Aeronave(
    id = 0L,
    apelido = apelido,
    categoria = CategoriaRegistro.valueOf(categoriaRegistro.uppercase()),
    marcas = Marcas.from(marcas),
    fabricante = Fabricante(fabricanteId),
    modelo = modelo,
    numeroSerie = numeroSerie
)

fun HorimetroDto.toDomain() = AeronaveHorimetro(
    totalVoo = horaTotalDecimal.toBigDecimal(),
    totalManutencao = horaManutencaoDecimal.toBigDecimal()
)

fun FabricanteDto.toDomain(): Fabricante = Fabricante(
    id = id ?: 0L,
    nome = nome
)
