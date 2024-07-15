package br.com.fiap.servicos.beleza.adapter.database.h2.servico;

import br.com.fiap.servicos.beleza.adapter.database.repository.ServicoRepository;
import br.com.fiap.servicos.beleza.usecase.database.servico.ServicoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.servico.ServicoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.servico.IAtualizarServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarServico implements IAtualizarServico {

    private final ServicoRepository servicoRepository;

    @Override
    @Transactional
    public ServicoResponseDB atualizarServico(long servicoId, ServicoRequestDB requestDB) {
        var servico = servicoRepository.findById(servicoId).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setNome(requestDB.nomeServico());
        servico.setDescricao(requestDB.descricaoServico());
        servico.setValor(requestDB.valor());
        servicoRepository.save(servico);
        return ServicoResponseDB.builder()
                .servicoId(servico.getId())
                .nomeServico(servico.getNome())
                .descricaoServico(servico.getDescricao())
                .valor(servico.getValor())
                .estabelecimentoId(servico.getEstabelecimento().getId())
                .build();
    }
}
