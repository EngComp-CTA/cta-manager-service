package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.domain.entities.*
import br.gov.ma.ctamanagerservice.domain.services.AeronaveService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [AeronaveController::class])
internal class AeronaveControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var aeronaveService: AeronaveService

    @Test
    fun `Quando chamar o metodo buscarAeronaves, deve retornar uma lista de AeronavesDto`() {
        val aeronave = Aeronave(
            1L,
            "falcao",
            Marcas(MarcaNacionalidade.PP, MarcaMatricula("matricula")),
            Fabricante(2L, "fabricante"),
            "MODELO",
            "NUMERO-SERIE",
            "SAE"
        )
        every { aeronaveService.getAll() } returns listOf(aeronave)

        mockMvc.perform(get("/aeronaves").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].id").value(aeronave.id))
//            .andExpect(jsonPath("\$.[0].marcas").value(aeronave.marcas))
//            .andExpect(jsonPath("\$.[1].fabricante").value(aeronave.fabricante))
            .andExpect(jsonPath("\$.[0].modelo").value(aeronave.modelo))
            .andExpect(jsonPath("\$.[0].numero_serie").value(aeronave.numeroSerie))
    }
}