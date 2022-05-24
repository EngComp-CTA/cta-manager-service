package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.BaseIntegrationTest
import br.gov.ma.ctamanagerservice.IntegrationUtils.PATH_FABRICANTE
import br.gov.ma.ctamanagerservice.adapters.db.AeronaveGatewayImpl
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FabricanteRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.fromDomain
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import br.gov.ma.ctamanagerservice.domain.services.AeronaveService
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.AfterEach
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
    private lateinit var fabricanteRepository: FabricanteRepository
    @Autowired
    private lateinit var aeronaveService: AeronaveService
    @Autowired
    private lateinit var aeronaveGatewayImpl: AeronaveGatewayImpl

    @AfterEach
    fun cleanup() {
        println(">> CLEANUP <<")
    }

    @Test
    fun `dado um fabricante novo, quando enviar POST, deve retornar o fabricante salvo e 200_OK `() {
        val novoFabricante = FabricanteDto(
            nome = "Helibras"
        )
        val entity = client.postForEntity<FabricanteDto>(PATH_FABRICANTE, novoFabricante)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotNull
        assertThat(entity.body?.nome).isEqualTo(novoFabricante.nome)
    }

    @Test
    fun `dado um fabricante novo, quando enviar POST, deve retornar o fabricante salvo e 200_OK rest assured`() {
        val nomeFabricante = "FORD"
        Given {
            body("{ \"nome\": \"$nomeFabricante\" }")
            contentType(ContentType.JSON)
        } When {
            post(PATH_FABRICANTE)
        } Then {
            statusCode(HttpStatus.OK.value())
            body("nome", equalTo(nomeFabricante))
        }
    }

    @Test
    fun `quando enviar POST com parametro invalido, deve retornar 400_BAD_REQUEST`() {
        val entidadeInvalida = mapOf("parametroInvalido" to 2L)
        val entity = client.postForEntity<String>(PATH_FABRICANTE, entidadeInvalida)
        println(entity)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `quando enviar PUT com nome atualizado, deve retornar 200_OK com o fabricante atualizado`() {
        val fabricante = fabricanteRepository.save(
            fromDomain(
                Fabricante(
                    id = 0L, nome = "Helibras"
                )
            )
        ).toDomain()
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
    fun `quando enviar GET com ID no parametro, deve retornar 200_OK e o fabricante encontrado`() {
        val fabricante = fabricanteRepository.save(
            fromDomain(
                Fabricante(
                    id = 0L, nome = "Helibras"
                )
            )
        ).toDomain()

        val entity = client.getForEntity<FabricanteDto>("$PATH_FABRICANTE/${fabricante.id}")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isEqualTo(fabricante.toDto())
    }

    @Test
    fun `dado um ID inválido, quando enviar GET com ID, deve retornar 404_NOT_FOUND`() {
        // TODO: 23/05/22 - corrigir handle exception
//        val entity = client.getForEntity<String>("$PATH_FABRICANTE/1000")
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `quando enviar GET, deve retornar 200_OK e todos os fabricantes cadastrados`() {
        // TODO: 23/05/22 - corrigir handle exception
//        val fabricantes = listOf(
//            fromDomain(
//                Fabricante(
//                    id = 0L, nome = "FORD"
//                )
//            ),
//            fromDomain(
//                Fabricante(
//                    id = 0L, nome = "CHEVROLET"
//                )
//            )
//        )
//
//        fabricanteRepository.saveAll(fabricantes)
//
//        val response = client.getForEntity<List<FabricanteDto>>(PATH_FABRICANTE)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(response.body).hasSize(fabricantes.size)
    }

    @Test
    fun `dado um ID válido, quando enviar DELETE com ID, deve retornar 201_NO_CONTENT`() {
        val fabricante = fabricanteRepository.save(
            fromDomain(
                Fabricante(
                    id = 0L, nome = "Helibras"
                )
            )
        ).toDomain()

        val response = client.exchange<String>("$PATH_FABRICANTE/${fabricante.id}", HttpMethod.DELETE)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `dado um ID inválido, quando enviar DELETE com ID, deve retornar 400_BAD_REQUEST`() {
        // TODO: 23/05/22 - corrigir handle exception
//        val response = client.exchange<String>("$PATH_FABRICANTE/2000", HttpMethod.DELETE)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}
