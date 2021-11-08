import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.openapi.generator") version "5.3.0"

    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "br.gov.ma"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")


    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    implementation("io.swagger.parser.v3:swagger-parser:2.0.20")
    implementation("org.openapitools:jackson-databind-nullable:0.2.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
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

val basePackage = "br.gov.ma.ctamanagerservice"
val pathSwagger = "$projectDir/src/main/resources/static/api-docs.yaml"

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main")
        }
    }
}


tasks.openApiValidate {
    inputSpec.set(pathSwagger)
}

tasks.openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set(pathSwagger)
    outputDir.set("$buildDir/generated")
    apiPackage.set("$basePackage.adapters.api")
    modelPackage.set("$basePackage.adapters.dto")
    modelNameSuffix.set("Dto")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
        )
    )
}