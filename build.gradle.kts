import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
//	id("org.openapi.generator") version "4.3.1"
    id("org.openapi.generator") version "5.1.1"

    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

//configure<SourceSetContainer> {
//    named("main") {
//        kotlin.srcDir("$buildDir/generated/src/main/java")
//    }
//}

group = "br.gov.ma"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

sourceSets{
    getByName("main") {
        java {
            srcDir("$buildDir/generated/src/main")
        }
    }
}


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

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileKotlin {
    dependsOn("openApiGenerate")
}

tasks.openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/static/api-docs.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("br.gov.ma.ctamanagerservice.adapters.api")
    modelPackage.set("br.gov.ma.ctamanagerservice.adapters.dto")
    invokerPackage.set("br.gov.ma.ctamanagerservice.adapters.invoker")
    modelNameSuffix.set("Dto")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
//            "delegatePattern" to "true",
            "hideGenerationTimestamp" to "true"
        )
    )
}

//tasks.openApiValidate.inputSpec = "$projectDir/src/main/resources/static/api-docs.yaml"
