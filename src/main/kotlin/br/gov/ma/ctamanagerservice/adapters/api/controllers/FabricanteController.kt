package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.FabricanteApi
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.services.FabricanteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class FabricanteController(
    private val fabricanteService: FabricanteService
) : FabricanteApi {
    override fun adicionarFabricante(fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        val novoFabricante = fabricanteDto.mapToDomain()
        return ResponseEntity.ok(
            fabricanteService.salvar(novoFabricante).mapToDto()
        )
    }

    override fun atualizarFabricante(fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        return ResponseEntity.ok(
            fabricanteService.atualizar(fabricanteDto.mapToDomain()).mapToDto()
        )
    }

    override fun buscarFabricantePorId(fabricanteId: Long): ResponseEntity<FabricanteDto> {
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

internal fun Fabricante.mapToDto(): FabricanteDto = FabricanteDto(
    id = id,
    nome = nome
)

internal fun FabricanteDto.mapToDomain(): Fabricante = Fabricante(
    id = id ?: 0L,
    nome = nome
)
