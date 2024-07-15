package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.IAtualizarEstabelecimento;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.IDeletarEstabelecimento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarEstabelecimento implements IAtualizarEstabelecimento {

    private final EstabelecimentoRepository estabelecimentoRepository;

    @Override
    @Transactional
    public EstabelecimentoResponseDB atualizarEstabelecimento(long estabelecimentoId, EstabelecimentoRequestDB requestDB) {
        var estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        estabelecimento.setNome(requestDB.nomeEstabelecimento());
        estabelecimento.setEndereco(requestDB.endereco());
        estabelecimento.setHorariosFuncionamento(requestDB.horarioFuncionamento());
        estabelecimentoRepository.save(estabelecimento);
        return EstabelecimentoResponseDB.builder()
                .estabelecimentoId(estabelecimento.getId())
                .nomeEstabelecimento(estabelecimento.getNome())
                .endereco(estabelecimento.getEndereco())
                .horarioFuncionamento(estabelecimento.getHorariosFuncionamento())
                .build();
    }
}