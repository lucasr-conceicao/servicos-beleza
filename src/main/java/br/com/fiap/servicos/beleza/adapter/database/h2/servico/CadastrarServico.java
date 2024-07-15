package br.com.fiap.servicos.beleza.adapter.database.h2.servico;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ServicoRepository;
import br.com.fiap.servicos.beleza.usecase.database.servico.ICadastrarServico;
import br.com.fiap.servicos.beleza.usecase.database.servico.ServicoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.servico.ServicoResponseDB;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarServico implements ICadastrarServico {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ServicoRepository servicoRepository;

    @Override
    @Transactional
    public ServicoResponseDB cadastrarServico(ServicoRequestDB requestDB) {
        var servico = montarServicoRequest(requestDB);
        servicoRepository.save(servico);
        return converterResponseDatabaseParaUC(servico);
    }

    private TabelaServico montarServicoRequest(ServicoRequestDB request) {
        val estabelecimento = buscarEstabelecimento(request.estabelecimentoId());
        return TabelaServico.builder()
                .nome(request.nomeServico())
                .descricao(request.descricaoServico())
                .valor(request.valor())
                .estabelecimento(estabelecimento)
                .build();
    }

    @Transactional(readOnly = true)
    private TabelaEstabelecimento buscarEstabelecimento(long estabelecimentoId) {
        return estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(
                () -> new NotFoundException("Estabelecimento nao encontrado"));
    }

    private ServicoResponseDB converterResponseDatabaseParaUC(TabelaServico tabela) {
        return ServicoResponseDB.builder()
                .servicoId(tabela.getId())
                .nomeServico(tabela.getNome())
                .descricaoServico(tabela.getDescricao())
                .valor(tabela.getValor())
                .estabelecimentoId(tabela.getEstabelecimento().getId())
                .build();
    }
}
