package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.repositories.AeronaveRepository
import org.springframework.stereotype.Service

@Service
class AeronaveService(private val repository: AeronaveRepository) {
    fun getAll(): List<Aeronave> = repository.findAll()
}