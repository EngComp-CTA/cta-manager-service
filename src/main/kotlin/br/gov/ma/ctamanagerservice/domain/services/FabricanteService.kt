package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.stereotype.Service

@Service
class FabricanteService(
    private val fabricanteGateway: FabricanteGateway
) : WithLogging() {
    fun recuperarTodos(): List<Fabricante> {
        LOG.info("recuperando todos os fabricantes")
        return fabricanteGateway.encontrarTudo().also {
            LOG.info("Total de ${it.size} fabricantes encontrados")
        }
    }

    fun criar(novoFabricante: Fabricante): Fabricante {
        LOG.info("criando novo fabricante=$novoFabricante")
        return salvar(novoFabricante)
    }

    private fun salvar(fabricante: Fabricante): Fabricante {
        LOG.info("salvando fabricante=$fabricante")
        return fabricanteGateway.salvar(fabricante).also {
            LOG.info("fabricante salvo com sucesso")
        }
    }

    fun recuperarPorId(fabricanteId: Long): Fabricante {
        LOG.info("recuperando fabricante por id=$fabricanteId")
        return fabricanteGateway.encontrarPorId(fabricanteId)?.also {
            LOG.info("encontrado fabricante=$it")
        } ?: throw NaoEncontradoException(mensagem = "Fabricante não encontrado")
    }

    fun removerPorId(fabricanteId: Long) {
        LOG.info("removendo fabricante por id=$fabricanteId")
        recuperarPorId(fabricanteId).let {
            fabricanteGateway.removerPorId(fabricanteId)
            LOG.info("removido fabricante id=$fabricanteId")
        }
    }

    fun alterar(id: Long, fabricante: Fabricante): Fabricante {
        LOG.info("alterando fabricante com id=$id")
        return recuperarPorId(id).run {
            salvar(fabricante.copy(id = id))
        }
    }
}
