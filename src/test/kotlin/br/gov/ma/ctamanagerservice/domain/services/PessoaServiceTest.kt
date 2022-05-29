package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.PessoaGateway
import br.gov.ma.ctamanagerservice.factories.umaPessoa
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PessoaServiceTest {

    private val pessoaGateway = mockk<PessoaGateway>()
    private val service = PessoaService(pessoaGateway)

    @Test
    fun `DADO uma pessoa nova QUANDO chamar o metodo criar DEVE salvar e retornar a pessoa salva`() {
        val pessoa = umaPessoa()
        every { pessoaGateway.salvar(pessoa) } returns pessoa

        val pessoaCriada = service.criar(pessoa)

        verify { pessoaGateway.salvar(pessoa) }
        assertThat(pessoaCriada).isEqualTo(pessoa)
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId DEVE retornar a pessoa recuperada pelo ID`() {
        val pessoa = umaPessoa()
        val ID = pessoa.id
        every { pessoaGateway.encontrarPorId(ID) } returns pessoa

        val resultado = service.recuperarPorId(ID)

        verify { pessoaGateway.encontrarPorId(ID) }
        assertThat(resultado.id)
            .isEqualTo(ID)
    }
    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId e nao encontrar a pessoa DEVE lancar NaoEncontradoException`() {
        val pessoa = umaPessoa()
        val ID = pessoa.id
        every { pessoaGateway.encontrarPorId(ID) } returns null

        val exception = assertThrows<NaoEncontradoException> {
            service.recuperarPorId(ID)
        }

        verify { pessoaGateway.encontrarPorId(ID) }
        assertThat(exception.tipo).isEqualTo("NOT_FOUND")
    }

    @Test
    fun `DADO um ID de uma pessoa QUANDO chamar o metodo atualizar DEVE atualizar a pessoa`() {
        val pessoa = umaPessoa()
        val ID = pessoa.id
        every { pessoaGateway.encontrarPorId(ID) } returns pessoa
        every { pessoaGateway.salvar(pessoa) } returns pessoa

        val pessoaAtualizada = service.alterar(ID, pessoa)

        verify { pessoaGateway.encontrarPorId(ID) }
        verify { pessoaGateway.salvar(pessoa) }
        assertThat(pessoaAtualizada)
            .isEqualTo(pessoa)
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo sem parametros DEVE recuperar todas as pessoas cadastradas`() {
        val pessoas = listOf(
            umaPessoa(),
            umaPessoa()
        )
        every { pessoaGateway.encontrarTudo() } returns pessoas

        val pessoasEncontradas = service.recuperarTudo()

        verify { pessoaGateway.encontrarTudo() }

        assertThat(pessoasEncontradas)
            .hasSize(2)
            .isEqualTo(pessoas)
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo com cpf DEVE recuperar a pessoa encontrada`() {
        val pessoa = umaPessoa()
        every { pessoaGateway.encontrarPorCpf(pessoa.cpf) } returns pessoa

        val pessoasEncontradas = service.recuperarTudo(cpf = pessoa.cpf)

        verify { pessoaGateway.encontrarPorCpf(pessoa.cpf) }

        assertThat(pessoasEncontradas)
            .hasSize(1)
            .isEqualTo(listOf(pessoa))
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo com cpf e nao encontrar a pessoa DEVE retornar uma lista vazia`() {
        val cpf = "12312312312"
        every { pessoaGateway.encontrarPorCpf(cpf) } returns null

        val pessoasEncontradas = service.recuperarTudo(cpf = cpf)

        verify { pessoaGateway.encontrarPorCpf(cpf) }

        assertThat(pessoasEncontradas)
            .isEmpty()
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo com nome DEVE recuperar todas as pessoas encontradas`() {
        val nome = "Cabral"
        val pessoas = listOf(umaPessoa(), umaPessoa())
        every { pessoaGateway.encontrarPorNome(nome) } returns pessoas

        val pessoasEncontradas = service.recuperarTudo(nome = nome)

        verify { pessoaGateway.encontrarPorNome(nome) }

        assertThat(pessoasEncontradas)
            .hasSize(2)
            .isEqualTo(pessoas)
    }

    @Test
    fun `QUANDO chamar o metodo recuperarTudo com nome e nao encontrar a pessoa DEVE retornar uma lista vazia`() {
        val nome = "Tiririca"
        every { pessoaGateway.encontrarPorNome(nome) } returns emptyList()

        val pessoasEncontradas = service.recuperarTudo(nome = nome)

        verify { pessoaGateway.encontrarPorNome(nome) }

        assertThat(pessoasEncontradas)
            .isEmpty()
    }
}
