package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.AeronaveHorimetro
import br.gov.ma.ctamanagerservice.domain.exceptions.HorimetroNaoSalvoException
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import br.gov.ma.ctamanagerservice.factories.comHorimetro
import br.gov.ma.ctamanagerservice.factories.umHorimetro
import br.gov.ma.ctamanagerservice.factories.umaAeronave
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AeronaveServiceTest {

    private val aeronaveGateway = mockk<AeronaveGateway>()
    private val service = AeronaveService(aeronaveGateway)

    @Test
    fun `QUANDO chamar o metodo recuperarTudo DEVE retornar a lista com todas as aeronaves`() {
        val aeronaves = listOf(umaAeronave(), umaAeronave(), umaAeronave())

        every { aeronaveGateway.encontrarTudo() } returns aeronaves
        val resultado = service.recuperarTudo()
        verify { aeronaveGateway.encontrarTudo() }

        assertThat(resultado)
            .isNotEmpty
            .hasSize(3)
            .isEqualTo(aeronaves)
    }

    @Test
    fun `DADO uma aeronave QUANDO chamar o metodo criar DEVE salvar e retornar a aeronave salva`() {
        val aeronave = umaAeronave()

        every { aeronaveGateway.salvar(aeronave) } returns aeronave
        val resultado = service.criar(aeronave)
        verify { aeronaveGateway.salvar(aeronave) }

        assertThat(resultado)
            .isEqualTo(aeronave)
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId DEVE retornar a aeronave recuperada pelo ID`() {
        val aeronave = umaAeronave()
        val ID = aeronave.id

        every { aeronaveGateway.encontrarPorId(ID) } returns aeronave
        val resultado = service.recuperarPorId(ID)
        verify { aeronaveGateway.encontrarPorId(ID) }

        assertThat(resultado)
            .isEqualTo(aeronave)
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId e nao encontrar a aeronave DEVE lancar NaoEncontradoException`() {
        val ID = 100L

        every { aeronaveGateway.encontrarPorId(ID) } returns null
        val exception = assertThrows<NaoEncontradoException> {
            service.recuperarPorId(ID)
        }
        verify { aeronaveGateway.encontrarPorId(ID) }
        assertThat(exception.tipo)
            .isEqualTo("NOT_FOUND")
    }

    @Test
    fun `DADO um ID de uma aeronave QUANDO chamar o metodo alterar DEVE atualizar a aeronave`() {
        val aeronaveOriginal = umaAeronave()
        val ID = aeronaveOriginal.id
        val aeronaveAlterada = umaAeronave(id = ID, apelido = "Aguia mortal")

        every { aeronaveGateway.encontrarPorId(ID) } returns aeronaveOriginal
        every { aeronaveGateway.salvar(aeronaveAlterada) } returns aeronaveAlterada
        val resultado = service.alterar(ID, aeronaveAlterada)
        verify { aeronaveGateway.salvar(aeronaveAlterada) }
        verify { aeronaveGateway.encontrarPorId(ID) }

        assertThat(resultado)
            .isEqualTo(aeronaveAlterada)
    }

    @Test
    fun `DADO um ID de uma aeronave e um Horimetro QUANDO chamar o metodo adicionarHoras DEVE adicionar o horimetro e retornar o horimetro salvo`() {
        val aeronaveOriginal = umaAeronave()
        val horimetro = umHorimetro()
        val ID = aeronaveOriginal.id

        val slotHorimetro = slot<AeronaveHorimetro>()
        every { aeronaveGateway.encontrarPorId(ID) } returns aeronaveOriginal
        every { aeronaveGateway.salvarHorimetro(ID, capture(slotHorimetro)) } returns true
        val resultado = service.adicionarHoras(ID, horimetro)
        verify { aeronaveGateway.encontrarPorId(ID) }
        verify { aeronaveGateway.salvarHorimetro(ID, capture(slotHorimetro)) }

        assertThat(resultado)
            .isEqualTo(horimetro)
        assertThat(slotHorimetro.captured)
            .isEqualTo(horimetro)
    }

    @Test
    fun `DADO um ID de uma aeronave e um Horimetro QUANDO chamar o metodo adicionarHoras DEVE somar o horimetro e retornar o horimetro salvo`() {
        val aeronave = umaAeronave().comHorimetro()
        val horimetro = umHorimetro()
        val ID = aeronave.id

        val slotHorimetro = slot<AeronaveHorimetro>()
        every { aeronaveGateway.encontrarPorId(ID) } returns aeronave
        every { aeronaveGateway.salvarHorimetro(ID, capture(slotHorimetro)) } returns true

        val resultado = service.adicionarHoras(ID, horimetro)

        verify { aeronaveGateway.encontrarPorId(ID) }
        verify { aeronaveGateway.salvarHorimetro(ID, capture(slotHorimetro)) }

        assertThat(resultado)
            .isEqualTo(slotHorimetro.captured)
    }

    @Test
    fun `DADO um ID de uma aeronave e um Horimetro QUANDO chamar o metodo adicionarHoras e acontecer um erro no gateway DEVE lancar a excecao HorimetroNaoSalvoException`() {
        val aeronaveOriginal = umaAeronave()
        val horimetro = umHorimetro()
        val ID = aeronaveOriginal.id

        every { aeronaveGateway.encontrarPorId(ID) } returns aeronaveOriginal
        every { aeronaveGateway.salvarHorimetro(ID, horimetro) } returns false
        val resultado = assertThrows<HorimetroNaoSalvoException> {
            service.adicionarHoras(ID, horimetro)
        }
        verify { aeronaveGateway.encontrarPorId(ID) }
        verify { aeronaveGateway.salvarHorimetro(ID, horimetro) }

        assertThat(resultado)
            .isEqualTo(HorimetroNaoSalvoException())
    }
}
