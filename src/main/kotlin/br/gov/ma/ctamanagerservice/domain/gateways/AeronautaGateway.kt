package br.gov.ma.ctamanagerservice.domain.gateways

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta

interface AeronautaGateway {
    fun encontrarTudo(): List<Aeronauta>
    fun encontrarPorId(id: Long): Aeronauta?
    fun salvar(aeronauta: Aeronauta): Aeronauta
    fun encontrarPorCodigoAnac(codigoAnac: Int): Aeronauta?
    fun encontrarPorPessoaId(pessoaId: Long): Aeronauta?
}
