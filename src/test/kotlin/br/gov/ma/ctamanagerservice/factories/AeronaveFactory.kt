package br.gov.ma.ctamanagerservice.factories

import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveRequestDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.Marcas
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

fun umFabricante(
    id: Long = nextLong(999L),
    nome: String = "HELIBRAS",
) = Fabricante(
    id = id,
    nome = nome
)

fun umaAeronave(
    id: Long = nextLong(999L),
    apelido: String = "GAVIAO",
    fabricante: Fabricante = umFabricante()
) = Aeronave(
    id = id,
    fabricante = fabricante,
    apelido = apelido,
    marcas = Marcas.from("PP-PAT"),
    numeroSerie = nextInt(9999),
    modelo = "ASB100ZX",
    categoria = CategoriaRegistro.SAE
)

fun Aeronave.comHorimetro(horimetro: AeronaveHorimetro = umHorimetro()) =
    this.copy(horimetroAeronave = horimetro)

fun Aeronave.toRequestDto() = AeronaveRequestDto(
    apelido = apelido,
    marcas = "$marcas",
    fabricanteId = fabricante.id,
    modelo = modelo,
    numeroSerie = numeroSerie,
    categoriaRegistro = categoria.name
)

fun umHorimetro() = AeronaveHorimetro(
    totalVoo = nextDecimal(),
    totalManutencao = nextDecimal()
)

private fun nextDecimal() = BigDecimal(nextInt(100))
    .divide(BigDecimal(10), 1, RoundingMode.HALF_UP)
