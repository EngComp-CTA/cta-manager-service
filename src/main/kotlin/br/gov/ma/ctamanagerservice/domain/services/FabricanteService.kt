package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.adapters.api.NotFoundException
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import org.springframework.stereotype.Service

@Service
class FabricanteService(private val repository: FabricanteGateway) {
    fun recuperarTodos(): List<Fabricante> = repository.encontrarTudo()
    fun salvar(fabricante: Fabricante) = repository.salvar(fabricante)
    fun recuperarPorId(fabricanteId: Long): Fabricante {
        return repository.encontrarPorId(fabricanteId) ?: throw NotFoundException(msg = "Fabricante não encontrado")
    }
    fun removerPorId(fabricanteId: Long) {
        recuperarPorId(fabricanteId).let {
            repository.removerPorId(fabricanteId)
        }
    }
    fun atualizar(fabricanteId: Long, fabricante: Fabricante): Fabricante {
        return recuperarPorId(fabricanteId).let {
            repository.salvar(fabricante.copy(id = fabricanteId))
        }
    }
}