package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.NotFoundException
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.services.FabricanteService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

private const val FABRICANTE_PATH = "/fabricante"
@WebMvcTest(FabricanteController::class)
internal class FabricanteControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var fabricanteService: FabricanteService

    @Test
    fun `Quando chamar o metodo recuperarPorId, deve retornar um fabricante`() {
        val fabricanteId = 10L;
        val fabricante = Fabricante(
            id = fabricanteId,
            nome = "Helibras"
        )
        every { fabricanteService.recuperarPorId(fabricanteId) } returns fabricante

        mockMvc.perform(MockMvcRequestBuilders.get("$FABRICANTE_PATH/$fabricanteId").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(fabricante.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.nome").value(fabricante.nome))
    }

    @Test
    fun `Quando chamar o metodo recuperar todos, deve retornar um fabricante`() {
        val fabricanteId = 10L;
        val fabricante = Fabricante(
            id = fabricanteId,
            nome = "Helibras"
        )
        every { fabricanteService.recuperarPorId(fabricanteId) } returns fabricante

        mockMvc.perform(MockMvcRequestBuilders.get("$FABRICANTE_PATH/$fabricanteId").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(fabricante.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.nome").value(fabricante.nome))
    }

    @Test
    fun `Quando chamar o recuperarPorId, deve lancar excecao`() {
        val fabricanteId = 1L;
        every { fabricanteService.recuperarPorId(fabricanteId) } throws NotFoundException(msg = "fabricante nao encontrado")

        mockMvc.perform(MockMvcRequestBuilders.get("$FABRICANTE_PATH/$fabricanteId").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}