package br.gov.ma.ctamanagerservice.domain.entities

class Marcas(private val marcaNacionalidade: MarcaNacionalidade, private val marcaMatricula: MarcaMatricula) {
    override fun toString(): String {
        return "Marcas{" +
                "marcaNacionalidade=" + marcaNacionalidade +
                ", marcaMatricula=" + marcaMatricula +
                '}'
    }
}