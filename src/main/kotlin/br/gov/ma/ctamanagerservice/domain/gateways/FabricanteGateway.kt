package br.gov.ma.ctamanagerservice.domain.gateways

import br.gov.ma.ctamanagerservice.domain.entities.Fabricante

interface FabricanteGateway {
    fun encontrarTudo(): List<Fabricante>
    fun encontrarPorId(fabricanteId: Long): Fabricante?
    fun salvar(fabricante: Fabricante): Fabricante
    fun removerPorId(fabricanteId: Long)
}
