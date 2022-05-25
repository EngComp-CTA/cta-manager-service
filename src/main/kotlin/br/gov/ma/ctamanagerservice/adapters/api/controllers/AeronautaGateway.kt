package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta

interface AeronautaGateway {
    fun encontrarTudo(): List<Aeronauta>
    fun encontrarPorId(id: Long): Aeronauta?
    fun salvar(aeronave: Aeronauta): Aeronauta
}
