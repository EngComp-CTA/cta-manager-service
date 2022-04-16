package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import org.springframework.stereotype.Service

@Service
class AeronaveService(private val repository: AeronaveGateway) {
    fun getAll(): List<Aeronave> = repository.buscarTodos()
    fun save(aeronave: Aeronave) = repository.salvar(aeronave)
}