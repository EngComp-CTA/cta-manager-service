package br.gov.ma.ctamanagerservice.domain.entities

import br.gov.ma.ctamanagerservice.util.formataEmHoras
import java.math.BigDecimal

data class AeronaveHorimetro(
    val totalVoo: BigDecimal,
    val totalManutencao: BigDecimal
) {
    val totalVooEmHoras = totalVoo.formataEmHoras()
    val totalManutencaoEmHoras = totalManutencao.formataEmHoras()

    override fun toString(): String {
        return "AeronaveHorimetro(totalVoo=$totalVoo, totalManutencao=$totalManutencao, totalVooEmHoras=$totalVooEmHoras, totalManutencaoEmHoras=$totalManutencaoEmHoras)"
    }

    operator fun plus(horimetroParaAdicionar: AeronaveHorimetro) = AeronaveHorimetro(
        totalVoo = totalVoo + horimetroParaAdicionar.totalVoo,
        totalManutencao = totalManutencao + horimetroParaAdicionar.totalManutencao
    )
}
