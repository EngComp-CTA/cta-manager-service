package br.gov.ma.ctamanagerservice.domain.repositories

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave

interface AeronaveRepository {
    fun findAll(): List<Aeronave>
}