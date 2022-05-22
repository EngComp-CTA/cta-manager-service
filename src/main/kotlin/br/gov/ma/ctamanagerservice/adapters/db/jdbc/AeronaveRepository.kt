package br.gov.ma.ctamanagerservice.adapters.db.jdbc

import br.gov.ma.ctamanagerservice.domain.entities.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.PagingAndSortingRepository
import java.math.BigDecimal
import java.time.LocalDateTime

const val AERONAVE_TABLE = "aeronave"

interface AeronaveRepository : PagingAndSortingRepository<AeronaveTable, Long>

@Table("aeronave_horimetro")
data class AeronaveHorimetro(
    @Id private val aeronaveId: Long,
    @Column private val totalVoo: BigDecimal,
    @Column private val totalManutencao: BigDecimal,
    @Column private val atualizadoEm: LocalDateTime
)

@Table(AERONAVE_TABLE)
data class AeronaveTable(
    @Id val id: Long,
    @Column private val apelido: String,
    @Column private val marcas: String,
    @Column private val fabricanteId: Long,
    @Column private val modelo: String,
    @Column private val numeroSerie: Int,
    @Column private val categoriaRegistro: String,
//    @Column private val fabricanteId: AggregateReference<FabricanteTable, Long>,
    @Column("aeronave_id") private val horimetro: AeronaveHorimetro? = null
) {
    fun toDomain(getFabricante: (idFabricante: Long) -> Fabricante) = Aeronave(
        id = id,
        apelido = apelido,
        marcas = marcas.let {
            Marcas(
                marcaNacionalidade = MarcaNacionalidade.valueOf(it.substring(0..1)),
                marcaMatricula = it.substring(2)
            )
        },
        fabricante = fabricanteId.let {
            getFabricante(it)
        },
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
            categoriaRegistro = aeronave.categoria.toString(),
            apelido = aeronave.apelido
        )
    }
}
