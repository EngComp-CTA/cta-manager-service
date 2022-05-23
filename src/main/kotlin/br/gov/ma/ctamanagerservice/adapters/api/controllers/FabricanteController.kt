package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.FabricanteApi
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDomain
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
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
        val novoFabricante = fabricanteDto.toDomain()
        return ResponseEntity.ok(
            fabricanteService.criar(novoFabricante).toDto()
        )
    }

    override fun atualizarFabricante(id: Long, fabricanteDto: FabricanteDto): ResponseEntity<FabricanteDto> {
        LOG.info("atualizando fabricanteDto=$fabricanteDto e id=$id")
        return ResponseEntity.ok(
            fabricanteService.alterar(id, fabricanteDto.toDomain()).toDto()
        )
    }

    override fun buscarFabricantePorId(fabricanteId: Long): ResponseEntity<FabricanteDto> {
        LOG.info("Buscando fabricante por id=$fabricanteId")
        return ResponseEntity.ok(
            fabricanteService.recuperarPorId(fabricanteId).toDto()
        )
    }

    override fun buscarFabricantes(): ResponseEntity<List<FabricanteDto>> {
        LOG.info("Buscando todos os fabricantes")
        return ResponseEntity.ok(
            fabricanteService.recuperarTodos().map(Fabricante::toDto)
        )
    }

    override fun removerFabricante(fabricanteId: Long): ResponseEntity<Unit> {
        LOG.info("Removendo fabricante por id=$fabricanteId")
        fabricanteService.removerPorId(fabricanteId)
        return ResponseEntity.noContent().build()
    }
}
