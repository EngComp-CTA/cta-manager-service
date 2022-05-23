package br.gov.ma.ctamanagerservice.domain.services

import br.gov.ma.ctamanagerservice.domain.exceptions.NaoEncontradoException
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import br.gov.ma.ctamanagerservice.factories.umFabricante
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FabricanteServiceTest {

    private val fabricanteGateway = mockk<FabricanteGateway>()
    private val service = FabricanteService(fabricanteGateway)

    @Test
    fun `QUANDO chamar o metodo recuperarTodos DEVE retornar a lista com todos fabricantes`() {
        val fabricantes = listOf(umFabricante(), umFabricante())

        every { fabricanteGateway.encontrarTudo() } returns fabricantes

        val fabricantesRecuperados = service.recuperarTodos()

        verify { fabricanteGateway.encontrarTudo() }
        assertThat(fabricantesRecuperados)
            .isNotEmpty
            .hasSize(2)
            .isEqualTo(fabricantes)
    }

    @Test
    fun `DADO um fabricante QUANDO chamar o metodo criar DEVE salvar e retornar o fabricante salvo`() {
        val fabricante = umFabricante()
        every { fabricanteGateway.salvar(fabricante) } returns fabricante

        val fabricanteCriado = service.criar(fabricante)

        verify { fabricanteGateway.salvar(fabricante) }
        assertThat(fabricanteCriado).isEqualTo(fabricante)
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId DEVE retornar o fabricante recuperado pelo ID`() {
        val fabricante = umFabricante()
        val ID = fabricante.id
        every { fabricanteGateway.encontrarPorId(ID) } returns fabricante

        val fabricanteRecuperado = service.recuperarPorId(ID)

        verify { fabricanteGateway.encontrarPorId(ID) }
        assertThat(fabricanteRecuperado.id)
            .isEqualTo(ID)
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo recuperarPorId e nao encontrar o fabricante DEVE lancar NaoEncontradoException`() {
        val ID = 99L
        every { fabricanteGateway.encontrarPorId(ID) } returns null

        val exception = assertThrows<NaoEncontradoException> {
            service.recuperarPorId(ID)
        }
        verify { fabricanteGateway.encontrarPorId(ID) }
        assertThat(exception.tipo).isEqualTo("NOT_FOUND")
    }

    @Test
    fun `DADO um ID QUANDO chamar o metodo removerPorId DEVE remover o fabricante recuperado pelo ID`() {
        val ID = 5L
        every { fabricanteGateway.removerPorId(ID) } just Runs
        every { fabricanteGateway.encontrarPorId(ID) } returns umFabricante()

        service.removerPorId(ID)

        verify { fabricanteGateway.removerPorId(ID) }
        verify { fabricanteGateway.encontrarPorId(ID) }
    }

    @Test
    fun `DADO um ID de um fabricante QUANDO chamar o metodo atualizar DEVE atualizar o fabricante`() {
        val fabricante = umFabricante()
        val ID = fabricante.id
        every { fabricanteGateway.encontrarPorId(ID) } returns fabricante
        every { fabricanteGateway.salvar(fabricante) } returns fabricante

        val fabricanteAtualizado = service.alterar(ID, fabricante)

        verify { fabricanteGateway.encontrarPorId(ID) }
        verify { fabricanteGateway.salvar(fabricante) }
        assertThat(fabricanteAtualizado)
            .isEqualTo(fabricante)
    }
}
