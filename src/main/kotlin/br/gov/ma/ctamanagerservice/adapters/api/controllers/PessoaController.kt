package br.gov.ma.ctamanagerservice.adapters.api.controllers

import br.gov.ma.ctamanagerservice.adapters.api.PessoaApi
import br.gov.ma.ctamanagerservice.adapters.dto.PessoaDto
import br.gov.ma.ctamanagerservice.adapters.ext.toDomain
import br.gov.ma.ctamanagerservice.adapters.ext.toDto
import br.gov.ma.ctamanagerservice.domain.entities.Pessoa
import br.gov.ma.ctamanagerservice.domain.services.PessoaService
import br.gov.ma.ctamanagerservice.util.WithLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PessoaController(
    private val pessoaService: PessoaService
) : PessoaApi, WithLogging() {

    override fun adicionarPessoa(pessoaDto: PessoaDto): ResponseEntity<PessoaDto> {
        LOG.info("adicionando nova pessoa")
        return ResponseEntity.ok(
            pessoaService.criar(pessoaDto.toDomain()).toDto()
        )
    }

    override fun atualizarPessoa(id: Long, pessoaDto: PessoaDto): ResponseEntity<PessoaDto> {
        LOG.info("atualizando pessoa com id=$id")
        return ResponseEntity.ok(
            pessoaService.alterar(id, pessoaDto.toDomain()).toDto()
        )
    }

    override fun buscarPessoaPorId(id: Long): ResponseEntity<PessoaDto> {
        LOG.info("buscando pessoa por id=$id")
        return ResponseEntity.ok(
            pessoaService.recuperarPorId(id).toDto()
        )
    }

    override fun buscarPessoas(nome: String?, cpf: String?): ResponseEntity<List<PessoaDto>> {
        LOG.info("buscando todas as pessoas com filtro")
        return ResponseEntity.ok(
            pessoaService.recuperarTudo(nome, cpf).map(Pessoa::toDto)
        )
    }
}
