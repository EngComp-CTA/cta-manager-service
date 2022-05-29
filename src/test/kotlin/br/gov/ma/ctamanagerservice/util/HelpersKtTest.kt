package br.gov.ma.ctamanagerservice.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal

internal class HelpersKtTest {

    @ParameterizedTest(name = "valorInicial={0} valorFormatado={1}")
    @MethodSource("valores")
    fun `QUANDO o valor do inteiro for menor que 10 DEVE formatar com 0 a esquerda`(valor: Int, resultado: String) {
        assertThat(valor.formataComZero())
            .isEqualTo(resultado)
    }

    @Test
    fun `DADO o valor da hora em decimal QUANDO chamar converteParaHoras DEVE formatar em horas`() {
        val horaDecimal = BigDecimal.valueOf(1.2)
        assertThat(horaDecimal.formataEmHoras())
            .isEqualTo("01:12")
    }

    companion object {
        @JvmStatic
        fun valores() = listOf(
            Arguments.of(0, "00"),
            Arguments.of(1, "01"),
            Arguments.of(2, "02"),
            Arguments.of(3, "03"),
            Arguments.of(4, "04"),
            Arguments.of(5, "05"),
            Arguments.of(6, "06"),
            Arguments.of(7, "07"),
            Arguments.of(8, "08"),
            Arguments.of(9, "09"),
            Arguments.of(10, "10"),
        )
    }
}
