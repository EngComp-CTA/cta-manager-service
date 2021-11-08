package br.gov.ma.ctamanagerservice.domain.entities

class Marcas(val marcaNacionalidade: MarcaNacionalidade, val marcaMatricula: MarcaMatricula) {
    override fun toString(): String {
        return "Marcas{" +
                "marcaNacionalidade=" + marcaNacionalidade +
                ", marcaMatricula=" + marcaMatricula +
                '}'
    }
}