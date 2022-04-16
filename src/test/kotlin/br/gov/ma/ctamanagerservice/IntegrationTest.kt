package br.gov.ma.ctamanagerservice

import br.gov.ma.ctamanagerservice.adapters.api.controllers.mapToDto
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FabricanteRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.fromDomain
import br.gov.ma.ctamanagerservice.adapters.dto.FabricanteDto
import br.gov.ma.ctamanagerservice.domain.entities.Fabricante
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
internal class IntegrationTest(
    @Autowired val client: TestRestTemplate
) {
    @Autowired
    private lateinit var fabricanteRepository: FabricanteRepository

    @AfterEach
    fun cleanup() {
        println(">> CLEANUP <<")
    }

    @BeforeEach
    fun setup() {
        fabricanteRepository.deleteAll()
    }

    companion object {
        @Container
        val container = postgres("13-alpine") {
            withDatabaseName("cta-manager-service")
            withUsername("cta-manager-service")
            withPassword("cta-manager-service")
        }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }
    }

    @Test
    fun `dado um fabricante novo, quando enviar POST, deve retornar o fabricante salvo e 200_OK `() {
        val novoFabricante = FabricanteDto(
            nome = "Helibras"
        )
        val entity = client.postForEntity<FabricanteDto>("/fabricante", novoFabricante)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotNull
        assertThat(entity.body?.nome).isEqualTo(novoFabricante.nome)
    }

    @Test
    fun `quando enviar POST com parametro invalido, deve retornar 400_BAD_REQUEST`() {
        val entidadeInvalida = mapOf("parametroInvalido" to 2L)
        val entity = client.postForEntity<String>("/fabricante", entidadeInvalida)
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
            url = "/fabricante",
            method = HttpMethod.PUT,
            requestEntity = HttpEntity(fabricante.mapToDto().copy(nome = nomeAtualizado))
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

        val entity = client.getForEntity<FabricanteDto>("/fabricante/${fabricante.id}")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isEqualTo(fabricante.mapToDto())
    }

    @Test
    fun `dado um ID inválido, quando enviar GET com ID, deve retornar 404_NOT_FOUND`() {
        val entity = client.getForEntity<String>("/fabricante/1000")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `quando enviar GET, deve retornar 200_OK e todos os fabricantes cadastrados`() {
        val fabricantes = listOf(
            fromDomain(
                Fabricante(
                    id = 0L, nome = "FORD"
                )
            ),
            fromDomain(
                Fabricante(
                    id = 0L, nome = "CHEVROLET"
                )
            )
        )

        fabricanteRepository.saveAll(fabricantes)

        val response = client.getForEntity<List<FabricanteDto>>("/fabricante")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).hasSize(fabricantes.size)
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

        val response = client.exchange<String>("/fabricante/${fabricante.id}", HttpMethod.DELETE)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `dado um ID inválido, quando enviar DELETE com ID, deve retornar 400_BAD_REQUEST`() {
        val response = client.exchange<String>("/fabricante/2000", HttpMethod.DELETE)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}

fun postgres(version: String, options: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:$version")).apply(options)