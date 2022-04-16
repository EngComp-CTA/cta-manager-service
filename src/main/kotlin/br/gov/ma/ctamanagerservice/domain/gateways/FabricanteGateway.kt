package br.gov.ma.ctamanagerservice.domain.gateways

import br.gov.ma.ctamanagerservice.domain.entities.Fabricante

interface FabricanteGateway {
    fun recuperarTodos(): List<Fabricante>
    fun recuperarPorId(fabricanteId: Long): Fabricante?
    fun salvar(fabricante: Fabricante): Fabricante
    fun removerPorId(fabricanteId: Long)
}