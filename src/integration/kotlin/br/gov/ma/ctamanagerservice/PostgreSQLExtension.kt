package br.gov.ma.ctamanagerservice

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgreSQLExtension : BeforeAllCallback {
    private lateinit var POSTGRES_CONTAINER: PostgreSQLContainer<Nothing>

    override fun beforeAll(context: ExtensionContext?) {
        val POSTGRES_CONTAINER = postgres("13-alpine") {
            println("PASSOU AQUI TB")
            withDatabaseName("cta-manager-service")
            withUsername("cta-manager-service")
            withPassword("cta-manager-service")
        }
        POSTGRES_CONTAINER.start()
        System.setProperty("spring.datasource.url", POSTGRES_CONTAINER.jdbcUrl)
    }

    fun postgres(version: String, options: JdbcDatabaseContainer<Nothing>.() -> Unit) =
        PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:$version")).apply(options)
}
