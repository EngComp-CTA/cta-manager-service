package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.BaseIntegrationTest
import br.gov.ma.ctamanagerservice.IntegrationUtils.PATH_AERONAVE
import br.gov.ma.ctamanagerservice.IntegrationUtils.PATH_FABRICANTE
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveDto
import br.gov.ma.ctamanagerservice.adapters.dto.AeronaveRequestDto
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Aeronave
import br.gov.ma.ctamanagerservice.domain.entities.CategoriaRegistro
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.gateways.AeronaveGateway
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import br.gov.ma.ctamanagerservice.factories.toRequestDto
import br.gov.ma.ctamanagerservice.factories.umFabricante
import br.gov.ma.ctamanagerservice.factories.umaAeronave
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

internal class AeronaveIntegration : BaseIntegrationTest() {
    @Autowired
    private lateinit var fabricanteGateway: FabricanteGateway
    @Autowired
    private lateinit var aeronaveGateway: AeronaveGateway

    private lateinit var FABRICANTE: Fabricante

    @BeforeEach
    fun cleanup() {
        FABRICANTE = fabricanteGateway.salvar(umFabricante(0L))
    }

    @Test
    fun `DADO uma aeronave nova, QUANDO enviar POST, DEVE retornar a aeronave salva e 200_OK `() {
        val aeronaveRequest = umaAeronave(0L, fabricante = FABRICANTE).toRequestDto()
        val response = client.postForEntity<AeronaveDto>(PATH_AERONAVE, aeronaveRequest)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull
        assertThat(response.body?.apelido).isEqualTo(aeronaveRequest.apelido)
        assertThat(response.body?.fabricante?.id).isEqualTo(aeronaveRequest.fabricanteId)
        assertThat(response.body?.modelo).isEqualTo(aeronaveRequest.modelo)
        assertThat(response.body?.numeroSerie).isEqualTo(aeronaveRequest.numeroSerie)
        assertThat(response.body?.categoriaRegistro).isEqualTo(aeronaveRequest.categoriaRegistro)
    }

    @Test
    fun `QUANDO enviar POST com parametro invalido, DEVE retornar 400_BAD_REQUEST`() {
        val entidadeInvalida = mapOf("parametro_invalido" to 2L)
        val entity = client.postForEntity<String>(PATH_AERONAVE, entidadeInvalida)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `QUANDO enviar PUT com nome atualizado, DEVE retornar 200_OK com aeronave atualizada`() {
        val aeronave = aeronaveGateway.salvar(umaAeronave(0L, fabricante = FABRICANTE))
        val aeronaveEditada = aeronave.copy(
            apelido = "NOVA AGUIA",
            categoria = CategoriaRegistro.TPX,
            numeroSerie = 1000
        )
        val response = client.exchange<AeronaveDto>(
            url = "$PATH_AERONAVE/${aeronave.id}",
            method = HttpMethod.PUT,
            requestEntity = HttpEntity(aeronaveEditada.toRequestDto())
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull
        assertThat(response.body?.apelido).isEqualTo(aeronaveEditada.apelido)
        assertThat(response.body?.modelo).isEqualTo(aeronaveEditada.modelo)
        assertThat(response.body?.numeroSerie).isEqualTo(aeronaveEditada.numeroSerie)
        assertThat(response.body?.marcas).isEqualTo(aeronaveEditada.marcas.toString())
        assertThat(response.body?.categoriaRegistro).isEqualTo(aeronaveEditada.categoria.name)
    }

    @Test
    fun `DADO um ID QUANDO enviar GET com ID no parametro, DEVE retornar 200_OK e a aeronave encontrada`() {
        val aeronave = aeronaveGateway.salvar(umaAeronave(0L, fabricante = FABRICANTE))
        val response = client.getForEntity<AeronaveDto>("$PATH_AERONAVE/${aeronave.id}")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(aeronave.toDto())
    }

    @Test
    fun `DADO um ID inválido, QUANDO enviar GET com ID, DEVE retornar 404_NOT_FOUND`() {
        val response = client.getForEntity<String>("$PATH_AERONAVE/1000")
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `QUANDO enviar GET, DEVE retornar 200_OK e todos as aeronaves cadastradas`() {
        val aeronaves = aeronaveGateway.run {
            listOf(
                salvar(umaAeronave(0L, "FALCAO", fabricante = FABRICANTE)),
                salvar(umaAeronave(0L, "ASA NOTURNO", fabricante = FABRICANTE))
            )
        }
        val response = client.exchange<List<AeronaveDto>>(
            url = PATH_AERONAVE,
            method = HttpMethod.GET
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body)
            .hasSize(aeronaves.size)
            .isEqualTo(aeronaves.map(Aeronave::toDto))
    }

    @Test
    fun `DADO uma aeronave com horimetro, QUANDO enviar um horimetro para adicionar, deve retornar 200_OK com o horimetro atualizado`() {
    }

    @Test
    fun `DADO uma aeronave sem horimetro, QUANDO enviar um horimetro para adicionar, deve retornar 200_OK com o horimetro criado`() {
    }
}
