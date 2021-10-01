package br.gov.ma.ctamanagerservice.adapters.persistence

import br.gov.ma.ctamanagerservice.adapters.persistence.schemas.AeronaveTable
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.repositories.AeronaveRepository
import org.springframework.stereotype.Component

@Component
class AeronaveRepositoryImpl(
    private val repository: AeronaveCrudRepository
) : AeronaveRepository {

    override fun findAll(): List<Aeronave> {
        return repository.findAll().map(AeronaveTable::toDomain)
    }
}