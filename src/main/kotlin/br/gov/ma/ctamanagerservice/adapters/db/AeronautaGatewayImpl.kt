package br.gov.ma.ctamanagerservice.adapters.db

import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronautaRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.AeronautaTable
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.PessoaRepository
import br.gov.ma.ctamanagerservice.adapters.db.jdbc.toTable
import br.gov.ma.ctamanagerservice.domain.entities.Aeronauta
import br.gov.ma.ctamanagerservice.domain.gateways.AeronautaGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AeronautaGatewayImpl(
    private val repository: AeronautaRepository,
    private val pessoaRepository: PessoaRepository
) : AeronautaGateway {
    override fun encontrarTudo(): List<Aeronauta> {
        return repository.findAll().map(::buildAeronauta)
    }

    override fun encontrarPorId(id: Long): Aeronauta? {
        return repository.findByIdOrNull(id)?.let(::buildAeronauta)
    }

    override fun salvar(aeronauta: Aeronauta): Aeronauta {
        return repository.save(aeronauta.toTable()).toDomain { aeronauta.pessoa }
    }

    override fun encontrarPorCodigoAnac(codigoAnac: Int): Aeronauta? {
        return repository.findByCanac(codigoAnac)?.let(::buildAeronauta)
    }

    override fun encontrarPorPessoaId(pessoaId: Long): Aeronauta? {
        return repository.findByPessoaId(pessoaId)?.let(::buildAeronauta)
    }

    private fun buildAeronauta(aeronautaTable: AeronautaTable): Aeronauta {
        return aeronautaTable.toDomain {
            pessoaRepository.findByIdOrNull(it)!!.toDomain()
        }
    }
}
