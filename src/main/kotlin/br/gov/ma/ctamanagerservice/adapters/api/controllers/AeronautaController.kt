package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.AeronautaApi
import br.gov.ma.ctamanagerservice.adapters.dto.AeronautaDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronautaRequestDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.services.AeronautaService
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AeronautaController(
    private val aeronautaService: AeronautaService
) : AeronautaApi, WithLogging() {

    override fun adicionarAeronauta(aeronautaRequestDto: AeronautaRequestDto): ResponseEntity<AeronautaDto> {
        LOG.info("adicionando novo aeronauta: $aeronautaRequestDto")
        return ResponseEntity.ok(
            aeronautaService.criar(aeronautaRequestDto.cpf, aeronautaRequestDto.codigoAnac).toDto()
        )
    }

    override fun buscarAeronauta(codigoAnac: Int?, cpf: String?, nome: String?): ResponseEntity<List<AeronautaDto>> {
        LOG.info("buscando aeronautas")
        return ResponseEntity.ok(
            aeronautaService.recuperarTudo(codigoAnac, cpf, nome).map(Aeronauta::toDto)
        )
    }

    override fun buscarAeronautaPorId(id: Long): ResponseEntity<AeronautaDto> {
        LOG.info("buscando aeronauta por id=$id")
        return ResponseEntity.ok(
            aeronautaService.recuperarPorId(id).toDto()
        )
    }
}
