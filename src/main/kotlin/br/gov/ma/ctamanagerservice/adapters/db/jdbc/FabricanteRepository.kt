package br.gov.ma.ctamanagerservice.adapters.db.jdbc

import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

const val FABRICANTE_TABLE = "fabricante"

interface FabricanteRepository : CrudRepository<FabricanteTable, Long>

@Table(FABRICANTE_TABLE)
data class FabricanteTable(
    @Id private val id: Long,
    @Column private val nome: String
) {
    fun toDomain() = Fabricante(
        id = id,
        nome = nome
    )
}

fun fromDomain(fabricante: Fabricante) = FabricanteTable(
    id = fabricante.id,
    nome = fabricante.nome
)