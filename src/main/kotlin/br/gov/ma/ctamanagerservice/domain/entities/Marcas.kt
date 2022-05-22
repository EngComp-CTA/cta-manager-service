package br.gov.ma.ctamanagerservice.domain.entities

class Marcas(val marcaNacionalidade: MarcaNacionalidade, val marcaMatricula: String) {
    override fun toString() = "$marcaNacionalidade-$marcaMatricula"
}
