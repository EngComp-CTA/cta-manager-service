package br.gov.ma.ctamanagerservice.util

import java.math.BigDecimal

private const val UM_MINUTO_EM_SEGUNDOS = 60L
fun BigDecimal.formataEmHoras(): String {
    val fracaoMinutos = rem(BigDecimal.ONE)
    val totalHoras = minus(fracaoMinutos).toInt()
    val totalMinutos = fracaoMinutos.multiply(BigDecimal.valueOf(UM_MINUTO_EM_SEGUNDOS)).toInt()
    return "${totalHoras.formataComZero()}:${totalMinutos.formataComZero()}"
}

fun Int.formataComZero(): String {
    return toString().let {
        if (it.length == 1) {
            it.padStart(2, '0')
        } else it
    }
}
