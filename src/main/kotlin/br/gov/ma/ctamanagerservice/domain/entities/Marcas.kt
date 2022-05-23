package br.gov.ma.ctamanagerservice.domain.entities

class Marcas(
    val marcaNacionalidade: MarcaNacionalidade,
    val marcaMatricula: String
) {
    override fun toString() = "$marcaNacionalidade$marcaMatricula"

    companion object {
        fun from(marcasComMascara: String): Marcas {
            return marcasComMascara.replace("-", "").uppercase().run {
                Marcas(
                    marcaNacionalidade = MarcaNacionalidade.valueOf(substring(0..1)),
                    marcaMatricula = substring(2)
                )
            }
        }
    }
}
