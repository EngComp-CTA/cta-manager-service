package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.exceptions.HorimetroNaoSalvoException
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal.ZERO

@Service
class AeronaveService(
    private val aeronaveGateway: AeronaveGateway
) : WithLogging() {

    fun recuperarTudo(): List<Aeronave> {
        LOG.info("recuperando todas as aeronaves")
        return aeronaveGateway.encontrarTudo().also {
            LOG.info("Total de ${it.size} aeronaves encontradas")
        }
    }

    fun criar(novaAeronave: Aeronave): Aeronave {
        LOG.info("criando nova aeronave=$novaAeronave")
        return salvar(novaAeronave)
    }

    fun recuperarPorId(id: Long): Aeronave {
        LOG.info("recuperando aeronave por id=$id")
        return aeronaveGateway.encontrarPorId(id)?.also {
            LOG.info("encontrado aeronave=$it")
        } ?: throw NaoEncontradoException(mensagem = "Aeronave não encontrada")
    }

    fun alterar(id: Long, aeronave: Aeronave): Aeronave {
        LOG.info("alterando aeronave com id=$id")
        return recuperarPorId(id).run {
            salvar(aeronave.copy(id = id))
        }
    }

    private fun salvar(aeronave: Aeronave): Aeronave {
        LOG.info("salvando aeronave=$aeronave")
        return aeronaveGateway.salvar(aeronave).also {
            LOG.info("aeronave salvo com sucesso")
        }
    }

    fun adicionarHoras(aeronaveId: Long, horimetroParaAdicionar: AeronaveHorimetro): AeronaveHorimetro {
        LOG.info("adicionando horas=$horimetroParaAdicionar para aeronave com id=$aeronaveId")
        return recuperarPorId(aeronaveId).let { aeronave ->
            val horimetro = aeronave.horimetroAeronave
                ?: AeronaveHorimetro(ZERO, ZERO)
            LOG.info("horimetro atual=$horimetro")
            val horimetroAtualizado = horimetro + horimetroParaAdicionar
            LOG.info("horimetro atualizado=$horimetroAtualizado")
            aeronaveGateway.salvarHorimetro(aeronaveId, horimetroAtualizado).takeIf { it }?.run {
                horimetroAtualizado
            } ?: throw HorimetroNaoSalvoException()
        }
    }
}
