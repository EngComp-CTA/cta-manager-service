package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.AeronaveApi
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveRequestDto
import br.gov.ma.ctamanagerservice.adapters.dto.HorimetroDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.Marcas
import br.gov.ma.ctamanagerservice.domain.services.AeronaveService
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AeronaveController(
    private val aeronaveService: AeronaveService
) : AeronaveApi, WithLogging() {

    override fun atualizarAeronave(id: Long, aeronaveRequestDto: AeronaveRequestDto): ResponseEntity<AeronaveDto> {
        LOG.info("atualizando aeronave com id=$id")
        return ResponseEntity.ok(
            aeronaveService.alterar(id, aeronaveRequestDto.toDomain()).toDto()
        )
    }

    override fun buscarAeronavePorId(id: Long): ResponseEntity<AeronaveDto> {
        LOG.info("buscando aeronave por id=$id")
        return ResponseEntity.ok(
            aeronaveService.recuperarPorId(id).toDto()
        )
    }

    override fun buscarAeronaves(): ResponseEntity<List<AeronaveDto>> {
        LOG.info("buscando todas as aeronaves")
        return ResponseEntity.ok(
            aeronaveService.recuperarTudo().map(Aeronave::toDto)
        )
    }

    override fun adicionarAeronave(aeronaveRequestDto: AeronaveRequestDto): ResponseEntity<AeronaveDto> {
        LOG.info("adicionando nova aeronave")
        return ResponseEntity.ok(
            aeronaveService.criar(aeronaveRequestDto.toDomain()).toDto()
        )
    }

    override fun adicionarHoras(id: Long, horimetroDto: HorimetroDto): ResponseEntity<HorimetroDto> {
        LOG.info("adicionando horas para aeronave com id=$id")
        return ResponseEntity.ok(
            aeronaveService.adicionarHoras(id, horimetroDto.toDomain()).toDto()
        )
    }
}

fun Aeronave.toDto() = AeronaveDto(
    id = id,
    numeroSerie = numeroSerie,
    modelo = modelo,
    fabricante = fabricante.toDto(),
    marcas = marcas.toString(),
    apelido = apelido,
    categoriaRegistro = categoria.name
)

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

fun AeronaveHorimetro.toDto() = HorimetroDto(
    horaTotalDecimal = totalVoo.toDouble(),
    horaManutencaoDecimal = totalManutencao.toDouble(),
    horaTotal = totalVooEmHoras,
    horaManutencao = totalManutencaoEmHoras
)
