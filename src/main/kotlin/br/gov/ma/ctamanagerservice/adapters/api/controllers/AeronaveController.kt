package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.AeronaveApi
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveRequestDto
import br.gov.ma.ctamanagerservice.adapters.dto.HorimetroDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDomain
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
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
