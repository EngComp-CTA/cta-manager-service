package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.util.WithLogging

class AeronautaService(
    private val aeronautaGateway: AeronautaGateway
) : WithLogging() {
    fun criar(novoAeronauta: Aeronauta): Aeronauta {
        LOG.info("criando novo aeronauta=$novoAeronauta")
        return salvar(novoAeronauta)
    }

    private fun salvar(novoAeronauta: Aeronauta): Aeronauta {
        LOG.info("salvando aeronauta=$novoAeronauta")
        return aeronautaGateway.salvar(novoAeronauta).also {
            LOG.info("aeronave salvo com sucesso")
        }
    }

    fun recuperarPorId(id: Long): Aeronauta {
        LOG.info("recuperando aeronauta por id=$id")
        return aeronautaGateway.encontrarPorId(id)?.also {
            LOG.info("encontrado aeronauta=$it")
        } ?: throw NaoEncontradoException(mensagem = "Aeronauta não encontrada")

    }

    fun recuperarTudo(codigoAnac: Int?, cpf: String?): List<Aeronauta> {
        LOG.info("recuperando todos os aeronautas")
        return aeronautaGateway.encontrarTudo().apply {
            LOG.info("Total de $size aeronaves encontradas")
        }
    }
}
