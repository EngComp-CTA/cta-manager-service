package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.FabricanteApi
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.services.FabricanteService
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.slf4j.LoggerFactory
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.validation.Valid


@RestController
class FabricanteController(
    private val fabricanteService: FabricanteService
) : FabricanteApi, WithLogging() {

    override fun adicionarFabricante(fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        LOG.info("adicionando novo fabricante $fabricanteDto")
        val novoFabricante = fabricanteDto.mapToDomain()
        return ResponseEntity.ok(
            fabricanteService.salvar(novoFabricante).mapToDto()
        )
    }

    override fun atualizarFabricante(id: Long, fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        LOG.info("adicionando novo fabricante $fabricanteDto")
        return ResponseEntity.ok(
            fabricanteService.atualizar(fabricanteDto.mapToDomain()).mapToDto()
        )
    }

    override fun buscarFabricantePorId(fabricanteId: Long): ResponseEntity<FabricanteDto> {
        LOG.info("buscando fabricando por id $fabricanteId")
        return ResponseEntity.ok(
            fabricanteService.recuperarPorId(fabricanteId).mapToDto()
        )
    }

    override fun buscarFabricantes(): ResponseEntity<List<FabricanteDto>> {
        return ResponseEntity.ok(
            fabricanteService.recuperarTodos().map(Fabricante::mapToDto)
        )
    }

    override fun removerFabricante(fabricanteId: Long): ResponseEntity<Unit> {
        fabricanteService.removerPorId(fabricanteId)
        return ResponseEntity.noContent().build()
    }

}

fun Fabricante.mapToDto(): FabricanteDto = FabricanteDto(
    id = id,
    nome = nome
)

fun FabricanteDto.mapToDomain(): Fabricante = Fabricante(
    id = id ?: 0L,
    nome = nome
)
