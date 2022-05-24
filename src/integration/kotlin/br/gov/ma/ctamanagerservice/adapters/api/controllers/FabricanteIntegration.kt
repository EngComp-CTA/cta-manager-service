package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.BaseIntegrationTest
import br.gov.ma.ctamanagerservice.IntegrationUtils.PATH_FABRICANTE
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.gateways.FabricanteGateway
import br.gov.ma.ctamanagerservice.factories.umFabricante
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

internal class FabricanteIntegration : BaseIntegrationTest() {
    @Autowired
    private lateinit var fabricanteGateway: FabricanteGateway

    @Test
    fun `DADO um fabricante novo, QUANDO enviar POST, DEVE retornar o fabricante salvo e 200_OK `() {
        val novoFabricante = umFabricante(0L).toDto()
        val response = client.postForEntity<FabricanteDto>(PATH_FABRICANTE, novoFabricante)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull
        assertThat(response.body?.nome).isEqualTo(novoFabricante.nome)
    }

    @Test
    fun `QUANDO enviar POST com parametro invalido, DEV retornar 400_BAD_REQUEST`() {
        val nomeFabricante = "FORD"
        Given {
            body("""{ "parametro_invalido": "$nomeFabricante" }""")
            contentType(ContentType.JSON)
        } When {
            post(PATH_FABRICANTE)
        } Then {
            statusCode(HttpStatus.BAD_REQUEST.value())
        }
    }

    @Test
    fun `QUANDO enviar PUT com nome atualizado, DEVE retornar 200_OK com o fabricante atualizado`() {
        val fabricante = fabricanteGateway.salvar(umFabricante(0L))
        val nomeAtualizado = "HELIBRAS ATUALIZADO"
        val entity = client.exchange<FabricanteDto>(
            url = "$PATH_FABRICANTE/${fabricante.id}",
            method = HttpMethod.PUT,
            requestEntity = HttpEntity(fabricante.toDto().copy(nome = nomeAtualizado))
        )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotNull
        assertThat(entity.body?.nome).isEqualTo(nomeAtualizado)
    }

    @Test
    fun `DADO um ID QUANDO enviar GET com ID no parametro, DEVE retornar 200_OK e o fabricante encontrado`() {
        val fabricante = fabricanteGateway.salvar(umFabricante(0L))
        val response = client.getForEntity<FabricanteDto>("$PATH_FABRICANTE/${fabricante.id}")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(fabricante.toDto())
    }

    @Test
    fun `DADO um ID inválido, QUANDO enviar GET com ID, DEVE retornar 404_NOT_FOUND`() {
        val response = client.getForEntity<String>("$PATH_FABRICANTE/1000")
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `QUANDO enviar GET, DEVE retornar 200_OK e todos os fabricantes cadastrados`() {
        val fabricantes = fabricanteGateway.run {
            listOf(
                salvar(umFabricante(0L, "HELIBRAS")),
                salvar(umFabricante(0L, "EUROSUL"))
            )
        }
        val response = client.exchange<List<FabricanteDto>>(
            url = PATH_FABRICANTE,
            method = HttpMethod.GET
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body)
            .hasSize(fabricantes.size)
            .isEqualTo(fabricantes.map(Fabricante::toDto))
    }

    @Test
    fun `DADO um ID válido, QUANDO enviar DELETE com ID, deve retornar 201_NO_CONTENT`() {
        val fabricante = fabricanteGateway.salvar(umFabricante(0L))
        val response = client.exchange<String>("$PATH_FABRICANTE/${fabricante.id}", HttpMethod.DELETE)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `DADO um ID inválido, QUANDO enviar DELETE com ID, DEVE retornar 400_BAD_REQUEST`() {
        val response = client.exchange<String>("$PATH_FABRICANTE/2000", HttpMethod.DELETE)
        println(response)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}
