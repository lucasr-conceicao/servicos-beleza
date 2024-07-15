package br.com.fiap.servicos.beleza.adapter.database.h2.avaliacao;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAvaliacaoEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.AvaliacaoEstabelecimentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.AvaliacaoEstabelecimentoRequest;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.ICadastrarAvaliacaoEstabelecimento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarAvaliacaoEstabelecimento implements ICadastrarAvaliacaoEstabelecimento {

    private final AvaliacaoEstabelecimentoRepository avaliacaoEstabelecimentoRepository;
    private final ClienteRepository clienteRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    @Transactional
    @Override
    public void cadastrarAvaliacaoEstabelecimento(AvaliacaoEstabelecimentoRequest request) {
        var avalicaoEstabelecimento = montarAvalicao(request);
        avaliacaoEstabelecimentoRepository.save(avalicaoEstabelecimento);
    }

    private TabelaAvaliacaoEstabelecimento montarAvalicao(AvaliacaoEstabelecimentoRequest request) {
        var cliente = buscarCliente(request.clienteId());
        var estabelecimento = buscarEstabelecimento(request.estabelecimentoId());
        return TabelaAvaliacaoEstabelecimento.builder()
                .id(request.id())
                .estabelecimentoId(estabelecimento.getId())
                .clienteId(cliente.getId())
                .pontuacao(request.pontuacao())
                .comentario(request.comentario())
                .build();

    }

    @Transactional(readOnly = true)
    private TabelaCliente buscarCliente(long clientId) {
        return  clienteRepository.findById(clientId).orElseThrow(
                () -> new NotFoundException("Cliente nao encontrado"));
    }

    @Transactional(readOnly = true)
    private TabelaEstabelecimento buscarEstabelecimento(long estabelecimentoId) {
        return  estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(
                () -> new NotFoundException("Estabelecimento nao encontrado"));
    }
}
