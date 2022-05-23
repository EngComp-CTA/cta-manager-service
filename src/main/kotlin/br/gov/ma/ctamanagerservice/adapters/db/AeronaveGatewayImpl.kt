package br.gov.ma.ctamanagerservice.adapters.db

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronaveRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronaveTable
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FabricanteRepository
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AeronaveGatewayImpl(
    private val repository: AeronaveRepository,
    private val fabricanteRepository: FabricanteRepository
) : AeronaveGateway {

    override fun encontrarTudo(): List<Aeronave> {
        return repository.findAll().map(::buildAeronave)
    }

    override fun encontrarPorId(id: Long): Aeronave? {
        return repository.findByIdOrNull(id)?.let(::buildAeronave)
    }

    override fun salvar(aeronave: Aeronave): Aeronave {
        val aeronaveTable = AeronaveTable.fromDomain(aeronave)
        return repository.save(aeronaveTable).toDomain { aeronave.fabricante }
    }

    override fun recuperarHorimetro(aeronaveId: Long): AeronaveHorimetro? {
        return repository.findAeronaveHorimetro(aeronaveId)?.toDomain()
    }

    override fun salvarHorimetro(aeronaveId: Long, aeronaveHorimetro: AeronaveHorimetro): Boolean {
        return with(repository) {
            findAeronaveHorimetro(aeronaveId)?.let {
                updateAeronaveHorimetro(aeronaveId, aeronaveHorimetro.totalVoo, aeronaveHorimetro.totalManutencao)
            } ?: insertAeronaveHorimetro(aeronaveId, aeronaveHorimetro.totalVoo, aeronaveHorimetro.totalManutencao)
        }
    }

    private fun buildAeronave(aeronaveTable: AeronaveTable): Aeronave {
        return aeronaveTable.toDomain { fabricanteRepository.findByIdOrNull(it)!!.toDomain() }
            .copy(horimetroAeronave = recuperarHorimetro(aeronaveTable.id))
    }
}
