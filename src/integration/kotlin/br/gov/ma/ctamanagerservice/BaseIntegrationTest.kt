package br.gov.ma.ctamanagerservice

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AERONAUTA_TABLE
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AERONAVE_HORIMETRO_TABLE
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AERONAVE_TABLE
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.FABRICANTE_TABLE
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.PESSOA_TABLE
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@ExtendWith(PostgreSQLExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseIntegrationTest {
    @LocalServerPort
    private val port = 0
    @Autowired
    lateinit var client: TestRestTemplate
    @Autowired
    lateinit var jdbc: JdbcTemplate

    @BeforeEach
    fun setup() {
        deleteFromTables(
            jdbc, AERONAVE_HORIMETRO_TABLE, AERONAVE_TABLE, FABRICANTE_TABLE, AERONAUTA_TABLE, PESSOA_TABLE
        )
        RestAssured.port = port
        RestAssured.config = RestAssuredConfig
            .config()
            .logConfig(
                LogConfig.logConfig()
                    .enableLoggingOfRequestAndResponseIfValidationFails()
            )
    }
}
