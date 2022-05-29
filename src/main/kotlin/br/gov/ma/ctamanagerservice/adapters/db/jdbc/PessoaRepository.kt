package br.gov.ma.ctamanagerservice.adapters.db.jdbc

import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.PagingAndSortingRepository

const val PESSOA_TABLE = "pessoa"

interface PessoaRepository : PagingAndSortingRepository<PessoaTable, Long> {
    fun findByCpf(cpf: String): PessoaTable?
    fun findByNomeContaining(nome: String): List<PessoaTable>
}

@Table(PESSOA_TABLE)
data class PessoaTable(
    @Id val id: Long,
    @Column private val cpf: String,
    @Column private val nome: String,
    @Column private val telefone: String?,
) {
    fun toDomain() = Pessoa(
        id = id,
        nome = nome,
        cpf = cpf,
        telefone = telefone
    )
}

fun Pessoa.toTable() = PessoaTable(
    id = id,
    nome = nome,
    cpf = cpf,
    telefone = telefone
)
