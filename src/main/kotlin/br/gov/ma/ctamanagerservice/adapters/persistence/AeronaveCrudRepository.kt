package br.gov.ma.ctamanagerservice.adapters.persistence

import br.gov.ma.ctamanagerservice.adapters.persistence.schemas.AeronaveTable
import org.springframework.data.repository.PagingAndSortingRepository

interface AeronaveCrudRepository : PagingAndSortingRepository<AeronaveTable, Long> {

}