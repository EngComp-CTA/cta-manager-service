package br.gov.ma.ctamanagerservice.adapters.db

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronaveRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronaveTable
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FabricanteRepository
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AeronaveGatewayImpl(
    private val repository: AeronaveRepository,
    private val fabricanteRepository: FabricanteRepository
) : AeronaveGateway {

    override fun buscarTodos(): List<Aeronave> {
        return repository.findAll().map {
            it.toDomain { fabricanteId ->
                fabricanteRepository.findByIdOrNull(fabricanteId)?.toDomain()
            }
        }
    }

    override fun salvar(aeronave: Aeronave): Aeronave {
        val aeronaveTable = AeronaveTable.fromDomain(aeronave)
        return repository.save(aeronaveTable).toDomain { aeronave.fabricante }
    }
}