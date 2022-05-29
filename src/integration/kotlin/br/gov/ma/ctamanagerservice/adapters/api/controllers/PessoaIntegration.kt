package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.BaseIntegrationTest
import br.gov.ma.ctamanagerservice.IntegrationUtils.PATH_PESSOA
import br.gov.ma.ctamanagerservice.adapters.dto.PessoaDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import br.gov.ma.ctamanagerservice.domain.gateways.PessoaGateway
import br.gov.ma.ctamanagerservice.factories.umaPessoa
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

internal class PessoaIntegration : BaseIntegrationTest() {
    @Autowired
    private lateinit var pessoaGateway: PessoaGateway

    @Test
    fun `DADO uma pessoa nova, QUANDO enviar POST, DEVE retornar a pessoa salva e 200_OK `() {
        val novaPessoa = umaPessoa().toDto()
        val response = client.postForEntity<PessoaDto>(PATH_PESSOA, novaPessoa)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull
        assertThat(response.body?.nome).isEqualTo(novaPessoa.nome)
    }

    @Test
    fun `QUANDO enviar PUT com nome atualizado, DEVE retornar 200_OK com a pessoa atualizada`() {
        val pessoa = pessoaGateway.salvar(umaPessoa(0L))
        val nomeAtualizado = "Rodrigo Frazao"
        val entity = client.exchange<PessoaDto>(
            url = "$PATH_PESSOA/${pessoa.id}",
            method = HttpMethod.PUT,
            requestEntity = HttpEntity(pessoa.toDto().copy(nome = nomeAtualizado))
        )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotNull
        assertThat(entity.body?.nome).isEqualTo(nomeAtualizado)
    }

    @Test
    fun `DADO um ID QUANDO enviar GET com ID no parametro, DEVE retornar 200_OK e a pessoa encontrada`() {
        val pessoa = pessoaGateway.salvar(umaPessoa(0L))
        val response = client.getForEntity<PessoaDto>("$PATH_PESSOA/${pessoa.id}")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(pessoa.toDto())
    }

    @Test
    fun `DADO um ID inválido, QUANDO enviar GET com ID, DEVE retornar 404_NOT_FOUND`() {
        val response = client.getForEntity<String>("$PATH_PESSOA/1000")
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `QUANDO enviar GET, DEVE retornar 200_OK e todos as pessoas cadastrados`() {
        val pessoas = pessoaGateway.run {
            listOf(
                salvar(umaPessoa(0L, "Hamilton")),
                salvar(umaPessoa(0L))
            )
        }
        val response = client.exchange<List<PessoaDto>>(
            url = PATH_PESSOA,
            method = HttpMethod.GET
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body)
            .hasSize(pessoas.size)
            .isEqualTo(pessoas.map(Pessoa::toDto))
    }

    @Test
    fun `QUANDO enviar GET filtrando por cpf, DEVE retornar 200_OK e a pessoa encontrada`() {
        val pessoas = pessoaGateway.run {
            listOf(
                salvar(umaPessoa(0L, "Hamilton")),
                salvar(umaPessoa(0L))
            )
        }
        val hamilton = pessoas.first()
        val params = mapOf("cpf" to hamilton.cpf)
        val response = client.exchange<List<PessoaDto>>(
            url = "$PATH_PESSOA?cpf={cpf}",
            method = HttpMethod.GET,
            uriVariables = params
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body)
            .hasSize(1)
            .isEqualTo(listOf(hamilton.toDto()))
    }

    @Test
    fun `QUANDO enviar GET filtrando por nome, DEVE retornar 200_OK e todas as pessoas encontradas`() {
        pessoaGateway.run {
            listOf(
                salvar(umaPessoa(0L, "Hamilton Cabral")),
                salvar(umaPessoa(0L, "Vettel Cabral")),
                salvar(umaPessoa(0L, "Rubens Barrichello"))
            )
        }
        val params = mapOf("nome" to "Cabral")
        val response = client.exchange<List<PessoaDto>>(
            url = "$PATH_PESSOA?nome={nome}",
            method = HttpMethod.GET,
            uriVariables = params
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body)
            .hasSize(2)
    }
}
