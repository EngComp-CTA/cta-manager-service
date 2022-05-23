package br.gov.ma.ctamanagerservice.factories

import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import kotlin.random.Random.Default.nextLong

fun umFabricante() = Fabricante(
    id = nextLong(999L),
    nome = "HELIBRAS"
)
