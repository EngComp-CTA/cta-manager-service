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

}