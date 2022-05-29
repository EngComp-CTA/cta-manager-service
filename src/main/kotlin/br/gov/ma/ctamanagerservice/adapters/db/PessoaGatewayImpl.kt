package br.gov.ma.ctamanagerservice.adapters.db

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.PessoaRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.PessoaTable
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.toTable
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import br.gov.ma.ctamanagerservice.domain.gateways.PessoaGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PessoaGatewayImpl(
    private val repository: PessoaRepository,
) : PessoaGateway {

    override fun encontrarTudo(): List<Pessoa> {
        return repository.findAll().map(PessoaTable::toDomain)
    }

    override fun encontrarPorId(id: Long): Pessoa? {
        return repository.findByIdOrNull(id)?.toDomain()
    }

    override fun encontrarPorCpf(cpf: String): Pessoa? {
        return repository.findByCpf(cpf)?.toDomain()
    }

    override fun encontrarPorNome(nome: String): List<Pessoa> {
        return repository.findByNomeContaining(nome).map(PessoaTable::toDomain)
    }

    override fun salvar(pessoa: Pessoa): Pessoa {
        return repository.save(pessoa.toTable()).toDomain()
    }
}
