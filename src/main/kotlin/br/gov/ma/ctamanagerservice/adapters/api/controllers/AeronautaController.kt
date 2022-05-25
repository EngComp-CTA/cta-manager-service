package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.AeronautaApi
import br.gov.ma.ctamanagerservice.adapters.dto.AeronautaDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDomain
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AeronautaController(
    private val aeronautaService: AeronautaService
) : AeronautaApi, WithLogging() {

    override fun adicionarAeronauta(aeronautaDto: AeronautaDto): ResponseEntity<AeronautaDto> {
        LOG.info("adicionando novo aeronauta")
        return ResponseEntity.ok(
            aeronautaService.criar(aeronautaDto.toDomain()).toDto()
        )
    }

    override fun buscarAeronauta(codigoAnac: Int?, cpf: String?): ResponseEntity<List<AeronautaDto>> {
        LOG.info("buscando todas os aeronautas")
        return ResponseEntity.ok(
            aeronautaService.recuperarTudo(codigoAnac, cpf).map(Aeronauta::toDto)
        )
    }

    override fun buscarAeronautaPorId(id: Long): ResponseEntity<AeronautaDto> {
        LOG.info("buscando aeronauta por id=$id")
        return ResponseEntity.ok(
            aeronautaService.recuperarPorId(id).toDto()
        )
    }
}

