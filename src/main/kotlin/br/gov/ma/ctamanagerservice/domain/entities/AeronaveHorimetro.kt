package br.gov.ma.ctamanagerservice.domain.entities

import br.gov.ma.ctamanagerservice.util.converteParaHoras
import java.math.BigDecimal

data class AeronaveHorimetro(
    val totalVoo: BigDecimal,
    val totalManutencao: BigDecimal
) {
    val totalVooEmHoras = totalVoo.converteParaHoras()
    val totalManutencaoEmHoras = totalManutencao.converteParaHoras()
    override fun toString(): String {
        return "AeronaveHorimetro(totalVoo=$totalVoo, totalManutencao=$totalManutencao, totalVooEmHoras=$totalVooEmHoras, totalManutencaoEmHoras=$totalManutencaoEmHoras)"
    }
}
