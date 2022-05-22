package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.NotFoundException
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.services.FabricanteService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val FABRICANTE_PATH = "/api/v1/fabricante"
@WebMvcTest(FabricanteController::class)
internal class FabricanteControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var fabricanteService: FabricanteService

    @Test
    fun `Quando chamar o metodo recuperarPorId, deve retornar um fabricante`() {
        val fabricanteId = 10L
        val fabricante = Fabricante(
            id = fabricanteId,
            nome = "Helibras"
        )
        every { fabricanteService.recuperarPorId(fabricanteId) } returns fabricante

        mockMvc.perform(get("$FABRICANTE_PATH/$fabricanteId").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(fabricante.id))
            .andExpect(jsonPath("\$.nome").value(fabricante.nome))
    }

    @Test
    fun `Quando chamar o metodo removerPorId todos, deve remover o fabricante por id`() {
        val FABRICANTE_ID = 10L
        every { fabricanteService.removerPorId(FABRICANTE_ID) } just Runs

        mockMvc.perform(delete("$FABRICANTE_PATH/$FABRICANTE_ID"))
            .andExpect(status().isOk)
    }
}
