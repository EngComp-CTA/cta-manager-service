package br.gov.ma.ctamanagerservice.adapters.ext

import br.gov.ma.ctamanagerservice.adapters.dto.AeronautaDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveRequestDto
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.dto.HorimetroDto
import br.gov.ma.ctamanagerservice.adapters.dto.PessoaDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.Marcas
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa

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

fun FabricanteDto.toDomain() = Fabricante(
    id = 0L,
    nome = nome
)

fun PessoaDto.toDomain() = Pessoa(
    id = 0L,
    nome = nome,
    cpf = cpf,
    telefone = telefone
)

fun AeronautaDto.toDomain() = Aeronauta(
    id = 0L,
    codigoAnac = codigoAnac,
    pessoa = pessoa.toDomain()
)
