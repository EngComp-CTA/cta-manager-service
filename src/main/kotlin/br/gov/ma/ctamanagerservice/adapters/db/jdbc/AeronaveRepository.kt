package br.gov.ma.ctamanagerservice.adapters.db.jdbc

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.Marcas
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.PagingAndSortingRepository
import java.math.BigDecimal
import java.time.LocalDateTime

const val AERONAVE_TABLE = "aeronave"
const val AERONAVE_HORIMETRO_TABLE = "aeronave_horimetro"
interface AeronaveRepository : PagingAndSortingRepository<AeronaveTable, Long> {
    @Query("SELECT * FROM $AERONAVE_HORIMETRO_TABLE WHERE aeronave_id = :aeronaveId")
    fun findAeronaveHorimetro(aeronaveId: Long): AeronaveHorimetroTable?

    @Modifying
    @Query("UPDATE $AERONAVE_HORIMETRO_TABLE SET total_voo=:totalVoo, total_manutencao=:totalManutencao, atualizado_em=now() WHERE aeronave_id=:aeronaveId")
    fun updateAeronaveHorimetro(aeronaveId: Long, totalVoo: BigDecimal, totalManutencao: BigDecimal): Boolean

    @Modifying
    @Query("INSERT INTO $AERONAVE_HORIMETRO_TABLE (aeronave_id, total_voo, total_manutencao, atualizado_em) VALUES(:aeronaveId, :totalVoo, :totalManutencao, now())")
    fun insertAeronaveHorimetro(aeronaveId: Long, totalVoo: BigDecimal, totalManutencao: BigDecimal): Boolean
}

@Table(AERONAVE_HORIMETRO_TABLE)
data class AeronaveHorimetroTable(
    @Id private val aeronaveId: Long,
    @Column private val totalVoo: BigDecimal,
    @Column private val totalManutencao: BigDecimal,
    @Column private val atualizadoEm: LocalDateTime
) {
    fun toDomain() = AeronaveHorimetro(
        totalManutencao = totalManutencao,
        totalVoo = totalVoo
    )
}

@Table(AERONAVE_TABLE)
data class AeronaveTable(
    @Id val id: Long,
    @Column private val apelido: String,
    @Column private val marcas: String,
    @Column private val fabricanteId: Long,
    @Column private val modelo: String,
    @Column private val numeroSerie: Int,
    @Column private val categoriaRegistro: String
) {
    fun toDomain(getFabricante: (Long) -> Fabricante) = Aeronave(
        id = id,
        apelido = apelido,
        marcas = Marcas.from(marcas),
        fabricante = getFabricante(fabricanteId),
        modelo = modelo,
        numeroSerie = numeroSerie,
        categoria = CategoriaRegistro.valueOf(categoriaRegistro)
    )

    companion object {
        fun fromDomain(aeronave: Aeronave) = AeronaveTable(
            id = aeronave.id,
            marcas = aeronave.marcas.toString().uppercase(),
            modelo = aeronave.modelo,
            fabricanteId = aeronave.fabricante.id,
            numeroSerie = aeronave.numeroSerie,
            categoriaRegistro = aeronave.categoria.name,
            apelido = aeronave.apelido
        )
    }
}
