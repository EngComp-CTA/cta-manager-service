package br.gov.ma.ctamanagerservice.domain.gateways

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro

interface AeronaveGateway {
    fun encontrarTudo(): List<Aeronave>
    fun encontrarPorId(id: Long): Aeronave?
    fun salvar(aeronave: Aeronave): Aeronave
    fun recuperarHorimetro(aeronaveId: Long): AeronaveHorimetro?
    fun salvarHorimetro(aeronaveId: Long, aeronaveHorimetro: AeronaveHorimetro): Boolean
}
