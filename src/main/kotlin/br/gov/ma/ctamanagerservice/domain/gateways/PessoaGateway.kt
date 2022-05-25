package br.gov.ma.ctamanagerservice.domain.gateways

import br.gov.ma.ctamanagerservice.domain.entities.Pessoa

interface PessoaGateway {
    fun encontrarTudo(): List<Pessoa>
    fun encontrarPorId(id: Long): Pessoa?
    fun encontrarPorCpf(cpf: String): Pessoa?
    fun encontrarPorNome(nome: String): List<Pessoa>
    fun salvar(aeronave: Pessoa): Pessoa
}
