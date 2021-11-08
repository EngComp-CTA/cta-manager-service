package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.AeronavesApi
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.adapters.ext.mapToDomain
import br.gov.ma.ctamanagerservice.adapters.ext.mapToDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.services.AeronaveService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AeronaveController(
    private val aeronaveService: AeronaveService
) : AeronavesApi {

    override fun buscarAeronaves(): ResponseEntity<List<AeronaveDto>> {
        val list = aeronaveService.getAll().map(Aeronave::mapToDto)
        return ResponseEntity.ok(list);
    }

    override fun salvarAeronave(aeronaveDto: AeronaveDto): ResponseEntity<AeronaveDto> {
        val aeronaveSalva = aeronaveService.save(aeronaveDto.mapToDomain()).mapToDto()
        return ResponseEntity.ok(aeronaveSalva)
    }
}