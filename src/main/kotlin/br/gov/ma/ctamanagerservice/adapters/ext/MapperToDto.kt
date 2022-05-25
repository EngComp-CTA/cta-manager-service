package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.adapters.dto.AeronautaDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.dto.HorimetroDto
import br.gov.ma.ctamanagerservice.adapters.dto.PessoaDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa

fun Aeronave.toDto() = AeronaveDto(
    id = id,
    numeroSerie = numeroSerie,
    modelo = modelo,
    fabricante = fabricante.toDto(),
    marcas = marcas.toString(),
    apelido = apelido,
    categoriaRegistro = categoria.name
)

fun AeronaveHorimetro.toDto() = HorimetroDto(
    horaTotalDecimal = totalVoo.toDouble(),
    horaManutencaoDecimal = totalManutencao.toDouble(),
    horaTotal = totalVooEmHoras,
    horaManutencao = totalManutencaoEmHoras
)

fun Fabricante.toDto() = FabricanteDto(
    id = id,
    nome = nome
)

fun Pessoa.toDto() = PessoaDto(
    id = id,
    cpf = cpf,
    nome = nome
)

fun Aeronauta.toDto() = AeronautaDto(
    id = id,
    codigoAnac = codigoAnac,
    pessoa = pessoa.toDto()
)
