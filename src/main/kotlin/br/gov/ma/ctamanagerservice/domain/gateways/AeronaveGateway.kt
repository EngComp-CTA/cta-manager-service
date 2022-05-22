package br.gov.ma.ctamanagerservice.domain.gateways

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave

interface AeronaveGateway {
    fun encontrarTudo(): List<Aeronave>
    fun salvar(aeronave: Aeronave): Aeronave
}
