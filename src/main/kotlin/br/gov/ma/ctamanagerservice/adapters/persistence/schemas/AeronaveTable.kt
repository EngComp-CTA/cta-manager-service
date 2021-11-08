package br.gov.ma.ctamanagerservice.adapters.persistence.schemas

import br.gov.ma.ctamanagerservice.domain.entities.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("aeronave")
data class AeronaveTable(
    @Id private val id: Long,
    @Column private val marcas: String,
    @Column private val fabricanteId: Long,
    @Column private val modelo: String,
    @Column private val numeroSerie: String
) {
    fun toDomain() = Aeronave(
        id = id,
        modelo = modelo,
        numeroSerie = numeroSerie,
        fabricante = Fabricante(id = fabricanteId, descricao = ""),
        marcas = Marcas(
            marcaMatricula = MarcaMatricula(descricao = marcas),
            marcaNacionalidade = MarcaNacionalidade.PP
        )
    )

    companion object {
        fun fromDomain(aeronave: Aeronave) = AeronaveTable(
            id = aeronave.id,
            marcas = "ALIBA",
            modelo = aeronave.modelo,
            fabricanteId = aeronave.fabricante.id,
            numeroSerie = aeronave.numeroSerie
        )
    }
}

fun AeronaveTable.fromDomain2(aeronave: Aeronave) = AeronaveTable(
    id = aeronave.id,
    marcas = aeronave.marcas.toString(),
    modelo = aeronave.modelo,
    fabricanteId = aeronave.fabricante.id,
    numeroSerie = aeronave.numeroSerie
)