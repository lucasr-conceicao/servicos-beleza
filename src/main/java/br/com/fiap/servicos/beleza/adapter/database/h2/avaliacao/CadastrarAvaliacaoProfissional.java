package br.com.fiap.servicos.beleza.adapter.database.h2.avaliacao;

import br.com.fiap.servicos.beleza.adapter.database.domain.*;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.AvaliacaoProfissionalRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.AvaliacaoProfissionalRequest;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.ICadastrarAvaliacaoProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarAvaliacaoProfissional implements ICadastrarAvaliacaoProfissional {

    private final AvaliacaoProfissionalRepository avaliacaoProfissionalRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    @Transactional
    @Override
    public void cadastrarAvaliacaoProfissional(AvaliacaoProfissionalRequest request) {
        var avalicaoProfissional = montarAvaliacao(request);
        avaliacaoProfissionalRepository.save(avalicaoProfissional);
    }

    private TabelaAvaliacaoProfissional montarAvaliacao(AvaliacaoProfissionalRequest request) {
        var cliente = buscarCliente(request.clienteId());
        var profissional = buscarProfissional(request.profissionalId());
        return TabelaAvaliacaoProfissional.builder()
                .id(request.id())
                .profissionalId(profissional.getId())
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
    private TabelaProfissional buscarProfissional(long profissionalId) {
        return  profissionalRepository.findById(profissionalId).orElseThrow(
                () -> new NotFoundException("Profissional nao encontrado"));
    }
}
