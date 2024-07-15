package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.repository.*;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.ICadastrarEstabelecimento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarEstabelecimento implements ICadastrarEstabelecimento {

    private final EstabelecimentoRepository estabelecimentoRepository;

    @Transactional
    @Override
    public EstabelecimentoResponseDB execute(EstabelecimentoRequestDB request) {
        var estabelecimento = montarEstabelecimentoRequest(request);
        estabelecimentoRepository.save(estabelecimento);
        return converterResponseDatabaseParaUC(estabelecimento);
    }

    private TabelaEstabelecimento montarEstabelecimentoRequest(EstabelecimentoRequestDB requestDB) {
        return TabelaEstabelecimento.builder()
                .nome(requestDB.nomeEstabelecimento())
                .endereco(requestDB.endereco())
                .horariosFuncionamento(requestDB.horarioFuncionamento())
                .build();
    }

    private EstabelecimentoResponseDB converterResponseDatabaseParaUC(TabelaEstabelecimento tabela) {
        return EstabelecimentoResponseDB.builder()
                .estabelecimentoId(tabela.getId())
                .nomeEstabelecimento(tabela.getNome())
                .endereco(tabela.getEndereco())
                .horarioFuncionamento(tabela.getHorariosFuncionamento())
                .build();
    }
}
