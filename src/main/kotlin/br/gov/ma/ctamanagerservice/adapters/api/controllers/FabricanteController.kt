package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.FabricanteApi
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.services.FabricanteService
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class FabricanteController(
    private val fabricanteService: FabricanteService
) : FabricanteApi, WithLogging() {

    override fun adicionarFabricante(fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        LOG.info("Adicionando novo fabricante $fabricanteDto")
        val novoFabricante = fabricanteDto.mapToDomain()
        return ResponseEntity.ok(
            fabricanteService.criar(novoFabricante).mapToDto()
        )
    }

    override fun atualizarFabricante(id: Long, fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        LOG.info("Alterando fabricanteDto=$fabricanteDto e id=$id")
        return ResponseEntity.ok(
            fabricanteService.atualizar(id, fabricanteDto.mapToDomain(),).mapToDto()
        )
    }

    override fun buscarFabricantePorId(fabricanteId: Long): ResponseEntity<FabricanteDto> {
        LOG.info("Buscando fabricante por id=$fabricanteId")
        return ResponseEntity.ok(
            fabricanteService.recuperarPorId(fabricanteId).mapToDto()
        )
    }

    override fun buscarFabricantes(): ResponseEntity<List<FabricanteDto>> {
        LOG.info("Buscando todos os fabricantes")
        return ResponseEntity.ok(
            fabricanteService.recuperarTodos().map(Fabricante::mapToDto)
        )
    }

    override fun removerFabricante(fabricanteId: Long): ResponseEntity<Unit> {
        LOG.info("Removendo fabricante por id=$fabricanteId")
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
