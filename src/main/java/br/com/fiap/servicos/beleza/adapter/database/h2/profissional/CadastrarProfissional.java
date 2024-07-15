package br.com.fiap.servicos.beleza.adapter.database.h2.profissional;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ProfissionalRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ProfissionalResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ICadastrarProfissional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarProfissional implements ICadastrarProfissional {

    private final ProfissionalRepository profissionalRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    @Transactional
    @Override
    public ProfissionalResponseDB execute(ProfissionalRequestDB request) {
        var profissional = montarProfissionalRequest(request);
        profissionalRepository.save(profissional);
        return converterResponseDatabaseParaUC(profissional);
    }

    private TabelaProfissional montarProfissionalRequest(ProfissionalRequestDB requestDB) {
        val estabelecimento = buscarEstabelecimento(requestDB.estabelecimentoId());
        return TabelaProfissional.builder()
                .nome(requestDB.nome())
                .especialidades(requestDB.especialidades())
                .tarifa(requestDB.tarifa())
                .horariosDisponiveis(requestDB.horariosDisponiveis())
                .estabelecimento(estabelecimento)
                .build();
    }

    @Transactional(readOnly = true)
    private TabelaEstabelecimento buscarEstabelecimento(long estabelecimentoId) {
        return estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(
                () -> new NotFoundException("Estabelecimento nao encontrado"));
    }

    private ProfissionalResponseDB converterResponseDatabaseParaUC(TabelaProfissional tabela) {
        return ProfissionalResponseDB.builder()
                .profissionalId(tabela.getId())
                .nome(tabela.getNome())
                .especialidades(tabela.getEspecialidades())
                .tarifa(tabela.getTarifa())
                .horariosDisponiveis(tabela.getHorariosDisponiveis())
                .estabelecimentoId(tabela.getEstabelecimento().getId())
                .build();
    }
}
