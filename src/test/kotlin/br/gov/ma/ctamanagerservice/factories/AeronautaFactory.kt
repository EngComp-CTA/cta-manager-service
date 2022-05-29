package br.gov.ma.ctamanagerservice.factories

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

fun umaPessoa(
    id: Long = nextLong(999L),
    nome: String = "Sergio Cabral",
    cpf: String = nextLong(11111111111, 99999999999).toString(),
    telefone: String = nextLong(98991111111, 98999999999).toString()
) = Pessoa(
    id = id,
    nome = nome,
    cpf = cpf,
    telefone = telefone
)

fun umAeronauta(
    id: Long = nextLong(999L),
    pessoa: Pessoa = umaPessoa(),
    codigoAnac: Int = nextInt(1111, 9999)
) = Aeronauta(
    id = id,
    pessoa = pessoa,
    codigoAnac = codigoAnac
)
