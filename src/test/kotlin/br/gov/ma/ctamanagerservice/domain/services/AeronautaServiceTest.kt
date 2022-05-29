package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.AeronautaGateway
import br.gov.ma.ctamanagerservice.factories.umAeronauta
import br.gov.ma.ctamanagerservice.factories.umaPessoa
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AeronautaServiceTest {

    private val aeronautaGateway = mockk<AeronautaGateway>()
    private val pessoaService = mockk<PessoaService>()
    private val service = AeronautaService(aeronautaGateway, pessoaService)

    @Test
    fun `DADO um aeronauta novo QUANDO chamar o metodo criar DEVE salvar e retornar o aeronauta salvo`() {
        val pessoa = umaPessoa()
        val cpf = pessoa.cpf
        val aeronauta = umAeronauta()
        val codigoAnac = aeronauta.codigoAnac
        val slot = slot<Aeronauta>()
        every { aeronautaGateway.salvar(capture(slot)) } returns aeronauta
        every { pessoaService.recuperarTudo(cpf = cpf) } returns listOf(pessoa)

        val aeronautaCriado = service.criar(cpf, codigoAnac)

        verify { aeronautaGateway.salvar(capture(slot)) }
        verify { pessoaService.recuperarTudo(cpf = cpf) }

        assertThat(slot.captured.pessoa).isEqualTo(pessoa)
        assertThat(aeronautaCriado)
            .isEqualTo(aeronauta)
    }

    @Test
    fun `QUANDO chamar o metodo criar e nao encontrar a pessoa pelo cpf DEVE lancar NaoEncontradoException`() {
        val cpf = "123123123"
        every { pessoaService.recuperarTudo(cpf = cpf) } returns emptyList()

        val exception = assertThrows<NaoEncontradoException> {
            service.criar(cpf, 1234)
        }
        verify { pessoaService.recuperarTudo(cpf = cpf) }

        assertThat(exception.mensagem)
            .isEqualTo("Pessoa não encontrada")
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId DEVE retornar o aeronauta recuperado pelo ID`() {
        val aeronauta = umAeronauta()
        val ID = aeronauta.id

        every { aeronautaGateway.encontrarPorId(ID) } returns aeronauta
        val resultado = service.recuperarPorId(ID)
        verify { aeronautaGateway.encontrarPorId(ID) }

        assertThat(resultado)
            .isEqualTo(aeronauta)
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId e nao encontrar o aeronauta DEVE lancar NaoEncontradoException`() {
        val ID = 31231L
        every { aeronautaGateway.encontrarPorId(ID) } returns null
        val exception = assertThrows<NaoEncontradoException> {
            service.recuperarPorId(ID)
        }
        verify { aeronautaGateway.encontrarPorId(ID) }

        assertThat(exception.mensagem)
            .isEqualTo("Aeronauta não encontrado")
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo sem parametros DEVE retornar todos os aeronautas`() {
        val aeronautas = listOf(umAeronauta())

        every { aeronautaGateway.encontrarTudo() } returns aeronautas
        val resultado = service.recuperarTudo()
        verify { aeronautaGateway.encontrarTudo() }

        assertThat(resultado)
            .hasSize(1)
            .isEqualTo(aeronautas)
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo pelo codigo anac DEVE retornar o aeronauta encontrado`() {
        val aeronauta = umAeronauta()
        val CODIGO_ANAC = aeronauta.codigoAnac

        every { aeronautaGateway.encontrarPorCodigoAnac(CODIGO_ANAC) } returns aeronauta
        val resultado = service.recuperarTudo(codigoAnac = CODIGO_ANAC)
        verify { aeronautaGateway.encontrarPorCodigoAnac(CODIGO_ANAC) }

        assertThat(resultado)
            .hasSize(1)
            .isEqualTo(listOf(aeronauta))
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo pelo cpf DEVE retornar o aeronauta encontrado`() {
        val aeronauta = umAeronauta()
        val pessoa = aeronauta.pessoa
        val (PESSOA_ID, _, CPF) = pessoa

        every { aeronautaGateway.encontrarPorPessoaId(PESSOA_ID) } returns aeronauta
        every { pessoaService.recuperarTudo(cpf = CPF) } returns listOf(pessoa)
        val resultado = service.recuperarTudo(cpf = CPF)
        verify { aeronautaGateway.encontrarPorPessoaId(PESSOA_ID) }
        verify { pessoaService.recuperarTudo(cpf = CPF) }

        assertThat(resultado)
            .hasSize(1)
            .isEqualTo(listOf(aeronauta))
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo pelo nome DEVE retornar os aeronautas encontrados`() {
        val aeronauta = umAeronauta()
        val pessoa = aeronauta.pessoa
        val (PESSOA_ID, NOME) = pessoa

        every { aeronautaGateway.encontrarPorPessoaId(PESSOA_ID) } returns aeronauta
        every { pessoaService.recuperarTudo(nome = NOME) } returns listOf(pessoa)
        val resultado = service.recuperarTudo(nome = NOME)
        verify { aeronautaGateway.encontrarPorPessoaId(PESSOA_ID) }
        verify { pessoaService.recuperarTudo(nome = NOME) }

        assertThat(resultado)
            .hasSize(1)
            .isEqualTo(listOf(aeronauta))
    }
}
