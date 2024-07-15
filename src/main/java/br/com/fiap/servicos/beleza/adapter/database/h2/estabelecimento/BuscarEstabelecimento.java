package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseProfissionaisDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoServicoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.IBuscarEstabelecimento;
import br.com.fiap.servicos.beleza.usecase.database.servico.ServicoResponseDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarEstabelecimento implements IBuscarEstabelecimento {

    private final EstabelecimentoRepository estabelecimentoRepository;

    @Override
    @Transactional
    public EstabelecimentoResponseDB buscarEstabelecimento(long estabelecimentoId) {
        var estabelecimento = estabelecimentoRepository.findById(estabelecimentoId);
        var estabelecimentoValidado = validarEstabelecimento(estabelecimento);
        return converterResponseDatabaseParaUC(estabelecimentoValidado);
    }

    private Optional<TabelaEstabelecimento> validarEstabelecimento(Optional<TabelaEstabelecimento> response) {
        if (!response.isPresent())
            throw new RuntimeException("Error");
        return response;
    }

    private EstabelecimentoResponseDB converterResponseDatabaseParaUC(Optional<TabelaEstabelecimento> response) {
        return EstabelecimentoResponseDB.builder()
                .estabelecimentoId(response.get().getId())
                .nomeEstabelecimento(response.get().getNome())
                .horarioFuncionamento(response.get().getHorariosFuncionamento())
                .endereco(response.get().getEndereco())
                .servicos(response.get().getServicos().stream().map(this::converterServicoParaResponseDB)
                        .collect(Collectors.toList()))
                .profissionais(response.get().getProfissionais().stream().map(this::converterProfissionalParaResponseDB)
                        .collect(Collectors.toList()))
                .build();
    }

    private EstabelecimentoServicoResponseDB converterServicoParaResponseDB(TabelaServico servico) {
        return EstabelecimentoServicoResponseDB.builder()
                .servicoId(servico.getId())
                .nomeServico(servico.getNome())
                .descricaoServico(servico.getDescricao())
                .valor(servico.getValor())
                .build();
    }

    private EstabelecimentoResponseProfissionaisDB converterProfissionalParaResponseDB(TabelaProfissional profissional) {
        return EstabelecimentoResponseProfissionaisDB.builder()
                .profissionalId(profissional.getId())
                .nome(profissional.getNome())
                .especialidades(profissional.getEspecialidades())
                .tarifa(profissional.getTarifa())
                .build();
    }
}
