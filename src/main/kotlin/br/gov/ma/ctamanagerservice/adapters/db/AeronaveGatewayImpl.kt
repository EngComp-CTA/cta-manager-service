package br.gov.ma.ctamanagerservice.adapters.db

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronaveCrudRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronaveTable
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import org.springframework.stereotype.Component

@Component
class AeronaveGatewayImpl(
    private val repository: AeronaveCrudRepository
) : AeronaveGateway {

    override fun buscarTodos(): List<Aeronave> {
        return repository.findAll().map(AeronaveTable::toDomain)
    }

    override fun salvar(aeronave: Aeronave): Aeronave {
        val aeronaveTable = AeronaveTable.fromDomain(aeronave)
        return repository.save(aeronaveTable).toDomain()
    }
}