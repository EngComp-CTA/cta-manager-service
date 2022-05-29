package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.AeronautaGateway
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.stereotype.Service

@Service
class AeronautaService(
    private val aeronautaGateway: AeronautaGateway,
    private val pessoaService: PessoaService
) : WithLogging() {

    fun criar(cpf: String, codigoAnac: Int): Aeronauta {
        LOG.info("Recuperando pessoa pelo cpf=$cpf")
        return pessoaService.recuperarTudo(cpf = cpf).firstOrNull()?.let {
            salvar(Aeronauta(id = 0L, pessoa = it, codigoAnac = codigoAnac))
        } ?: throw NaoEncontradoException(mensagem = "Pessoa não encontrada")
    }

    private fun salvar(novoAeronauta: Aeronauta): Aeronauta {
        LOG.info("salvando aeronauta=$novoAeronauta")
        return aeronautaGateway.salvar(novoAeronauta).also {
            LOG.info("aeronauta salvo com sucesso")
        }
    }

    fun recuperarPorId(id: Long): Aeronauta {
        LOG.info("recuperando aeronauta por id=$id")
        return aeronautaGateway.encontrarPorId(id)?.also {
            LOG.info("encontrado aeronauta=$it")
        } ?: throw NaoEncontradoException(mensagem = "Aeronauta não encontrado")
    }

    fun recuperarTudo(codigoAnac: Int? = null, cpf: String? = null, nome: String? = null): List<Aeronauta> {
        LOG.info("recuperando aeronautas com o filtro { codigoAnac=$codigoAnac, cpf=$cpf, nome=$nome }")
        val aeronauta = codigoAnac?.let {
            aeronautaGateway.encontrarPorCodigoAnac(it)
        } ?: recuperarAeronautaPorPessoa(cpf, nome)
        return aeronauta?.let {
            listOf(it)
        } ?: aeronautaGateway.encontrarTudo().apply {
            LOG.info("Total de $size aeronautas encontrados")
        }
    }

    private fun recuperarAeronautaPorPessoa(cpf: String?, nome: String?): Aeronauta? {
        return (cpf ?: nome)?.let {
            pessoaService.recuperarTudo(cpf = cpf, nome = nome).firstOrNull()?.let {
                aeronautaGateway.encontrarPorPessoaId(it.id)
            }
        }
    }
}
