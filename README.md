# `CTA-MANAGER-SERVICE`

---

## Requisitos

- JDK8
- Docker(link) e DockerCompose(link)

---
## Stack e Tecnologias Utilizada
- Kotlin
- SpringBoot (SpringDataJdbc, SpringWeb)
- OpenApiGenerate (API First)
- Flyway
- Postgres

#### O projeto utiliza um modelo de arquitetura hexagonal com adaptações utilizando conceitos do DDD (Domain Driven Design) , na camada de API é utilizado a abordagem de API First, determinando antes o contrato da API para implementação

---

## Variaveis de Ambiente

As variaveis de ambiente padrão do projeto ficam no arquivo [`src/main/resources/application.yml`](`src/main/resources/application.yml`)

---
## Como rodar o projeto local

Com o docker instalado , rodar o comando: `docker-compose up -d` para subir os containers

````sh
# Validar swagger para geração das clases da camada de API
./gradlew openApiValidate

# Gerar os dtos utilizados na api
./gradlew openApiGenerate

# Subir o server local 
./gradlew bootRun

# Verificar build para deploy
./gradlew clean build
````
---
## Tests

- * O Resultado dos testes fica em `build/reports/testes/test/index.html` *