package br.gov.ma.ctamanagerservice.adapters.db.jdbc

import br.gov.ma.ctamanagerservice.domain.entities.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.PagingAndSortingRepository

interface AeronaveCrudRepository : PagingAndSortingRepository<AeronaveTable, Long>

@Table("aeronave")
data class AeronaveTable(
    @Id private val id: Long,
    @Column private val apelido: String,
    @Column private val marcas: String,
    @Column private val fabricanteId: Long,
    @Column private val modelo: String,
    @Column private val numeroSerie: String,
    @Column private val categoriaRegistro: String
) {
    fun toDomain() = Aeronave(
        id = id,
        apelido = apelido,
        marcas = Marcas(
            marcaMatricula = MarcaMatricula(descricao = marcas),
            marcaNacionalidade = MarcaNacionalidade.PP
        ),
        fabricante = Fabricante(id = fabricanteId, nome = ""),
        modelo = modelo,
        numeroSerie = numeroSerie,
        categoria = categoriaRegistro
    )

    companion object {
        fun fromDomain(aeronave: Aeronave) = AeronaveTable(
            id = aeronave.id,
            marcas = "ALIBA",
            modelo = aeronave.modelo,
            fabricanteId = aeronave.fabricante.id,
            numeroSerie = aeronave.numeroSerie,
            categoriaRegistro = "",
            apelido = ""
        )
    }
}

fun fromDomain(aeronave: Aeronave) = AeronaveTable(
    id = aeronave.id,
    marcas = aeronave.marcas.toString(),
    modelo = aeronave.modelo,
    fabricanteId = aeronave.fabricante.id,
    numeroSerie = aeronave.numeroSerie,
    categoriaRegistro = "",
    apelido = ""
)