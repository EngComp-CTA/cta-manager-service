package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.entities.MarcaNacionalidade
import br.gov.ma.ctamanagerservice.domain.entities.Marcas
import br.gov.ma.ctamanagerservice.domain.services.AeronaveService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [AeronaveController::class])
internal class AeronaveControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var aeronaveService: AeronaveService

    @Test
    fun `Quando chamar o metodo buscarAeronaves, deve retornar uma lista de AeronavesDto`() {
        val aeronave = Aeronave(
            1L,
            "falcao",
            Marcas(MarcaNacionalidade.PP, "matricula"),
            Fabricante(2L, "fabricante"),
            "MODELO",
            1234,
            CategoriaRegistro.SAE
        )
        every { aeronaveService.recuperarTudo() } returns listOf(aeronave)

        mockMvc.perform(get("/api/v1/aeronave").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].id").value(aeronave.id))
//            .andExpect(jsonPath("\$.[0].marcas").value(aeronave.marcas))
//            .andExpect(jsonPath("\$.[1].fabricante").value(aeronave.fabricante))
            .andExpect(jsonPath("\$.[0].modelo").value(aeronave.modelo))
            .andExpect(jsonPath("\$.[0].numero_serie").value(aeronave.numeroSerie))
    }
}
