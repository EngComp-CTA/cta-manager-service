package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.adapters.api.NotFoundException
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import org.springframework.stereotype.Service

@Service
class FabricanteService(private val repository: FabricanteGateway) {
    fun recuperarTodos(): List<Fabricante> = repository.recuperarTodos()
    fun salvar(fabricante: Fabricante) = repository.salvar(fabricante)
    fun recuperarPorId(fabricanteId: Long): Fabricante {
        return repository.recuperarPorId(fabricanteId) ?: throw NotFoundException(msg = "Fabricante não encontrado")
    }
    fun removerPorId(fabricanteId: Long) {
        recuperarPorId(fabricanteId).let {
            repository.removerPorId(fabricanteId)
        }
    }
    fun atualizar(fabricante: Fabricante): Fabricante {
        return recuperarPorId(fabricante.id).let {
            repository.salvar(fabricante)
        }
    }
}