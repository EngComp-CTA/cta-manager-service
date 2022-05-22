package br.gov.ma.ctamanagerservice.adapters.db

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FabricanteRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FabricanteTable
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.fromDomain
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FabricanteGatewayImpl(
    private val repository: FabricanteRepository
) : FabricanteGateway {

    override fun encontrarTudo(): List<Fabricante> {
        return repository.findAll().map(FabricanteTable::toDomain)
    }

    override fun encontrarPorId(fabricanteId: Long): Fabricante? {
        return repository.findByIdOrNull(fabricanteId)?.toDomain()
    }

    override fun salvar(fabricante: Fabricante): Fabricante {
        return repository.save(fromDomain(fabricante)).toDomain()
    }

    override fun removerPorId(fabricanteId: Long) {
        repository.deleteById(fabricanteId)
    }
}
