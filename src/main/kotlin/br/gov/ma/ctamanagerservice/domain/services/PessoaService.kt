package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.PessoaGateway
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.stereotype.Service

@Service
class PessoaService(
    private val pessoaGateway: PessoaGateway
) : WithLogging() {

    fun criar(novaPessoa: Pessoa): Pessoa {
        LOG.info("criando nova pessoa=$novaPessoa")
        return salvar(novaPessoa)
    }

    fun alterar(id: Long, pessoa: Pessoa): Pessoa {
        LOG.info("alterando pessoa com id=$id")
        return recuperarPorId(id).run {
            salvar(pessoa.copy(id = id))
        }
    }

    fun recuperarPorId(id: Long): Pessoa {
        LOG.info("recuperando pessoa por id=$id")
        return pessoaGateway.encontrarPorId(id)?.also {
            LOG.info("encontrado pessoa=$it")
        } ?: throw NaoEncontradoException(mensagem = "Pessoa não encontrada")
    }

    fun recuperarTudo(nome: String? = null, cpf: String? = null): List<Pessoa> {
        LOG.info("recuperando todas as pessoas por filtro")
        return cpf?.let {
            LOG.info("recuperando pessoa por cpf=$it")
            pessoaGateway.encontrarPorCpf(it)?.run {
                listOf(this)
            } ?: emptyList()
        } ?: nome?.let {
            LOG.info("recuperando pessoa por nome=$it")
            pessoaGateway.encontrarPorNome(it).apply {
                LOG.info("Total de $size pessoas encontradas")
            }
        } ?: pessoaGateway.encontrarTudo().apply {
            LOG.info("Total de $size pessoas encontradas")
        }
    }

    private fun salvar(pessoa: Pessoa): Pessoa {
        LOG.info("salvando pessoa=$pessoa")
        return pessoaGateway.salvar(pessoa).also {
            LOG.info("pessoa salvo com sucesso")
        }
    }
}
