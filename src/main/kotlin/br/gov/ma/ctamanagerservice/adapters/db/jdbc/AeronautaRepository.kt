package br.gov.ma.ctamanagerservice.adapters.db.jdbc

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

const val AERONAUTA_TABLE = "aeronauta"
interface AeronautaRepository : CrudRepository<AeronautaTable, Long> {
    fun findByCanac(codigoAnac: Int): AeronautaTable?
    fun findByPessoaId(pessoaId: Long): AeronautaTable?
}

@Table(AERONAUTA_TABLE)
data class AeronautaTable(
    @Id val id: Long,
    @Column private val pessoaId: Long,
    @Column private val canac: Int,
) {
    fun toDomain(getPessoa: (Long) -> Pessoa) = Aeronauta(
        id = id,
        pessoa = getPessoa(pessoaId),
        codigoAnac = canac
    )
}

fun Aeronauta.toTable() = AeronautaTable(
    id = id,
    pessoaId = pessoa.id,
    canac = codigoAnac
)
