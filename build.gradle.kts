import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.openapi.generator") version "5.3.0"
    id("com.coditory.integration-test") version "1.3.0"
//    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"

    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "br.gov.ma"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

val testcontainersVersion = "1.16.2"
val postgresVersion = "42.2.14"
val junitJupiterVersion = "5.8.2"
val restAssuredVersion = "5.0.1"
extra["testcontainersVersion"] = "1.16.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql:$postgresVersion")

//    springdoc-openapi-kotlin
//    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.7")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.7")
//    implementation("io.swagger.parser.v3:swagger-parser:2.0.20")
//    implementation("org.openapitools:jackson-databind-nullable:0.2.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    integrationImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    integrationImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    integrationImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    integrationImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    integrationImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    integrationImplementation("io.rest-assured:json-path:$restAssuredVersion")
    integrationImplementation("io.rest-assured:xml-path:$restAssuredVersion")
    integrationImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
//    testImplementation("io.mockk:mockk:1.10.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileKotlin {
    dependsOn("openApiGenerate")
}

ktlint {
//    verbose = false
    filter {
        exclude("**/generated/src/main/kotlin/br/gov/ma/ctamanagerservice/adapters/api/*")
    }
}

val basePackage = "br.gov.ma.ctamanagerservice"
val pathSwagger = "$projectDir/src/main/resources/static/api-docs.yaml"

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main/kotlin")
        }
    }
}

tasks.openApiValidate {
    inputSpec.set(pathSwagger)
}
tasks.openApiGenerate {
    mustRunAfter(
        "runKtlintCheckOverMainSourceSet"
    )
}

val excludePackages = listOf(
    "**/br/gov/ma/ctamanagerservice/adapters/api/*",
    "**/br/gov/ma/ctamanagerservice/adapters/dto/*",
)
extra["excludePackages"] = excludePackages

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set(pathSwagger)
    outputDir.set("$buildDir/generated")
    apiPackage.set("$basePackage.adapters.api")
    modelPackage.set("$basePackage.adapters.dto")
    modelNameSuffix.set("Dto")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "gradleBuildFile" to "false",
            "exceptionHandler" to "false",
            "enumPropertyNaming" to "UPPERCASE"
        )
    )
}
