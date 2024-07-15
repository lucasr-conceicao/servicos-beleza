package br.com.fiap.servicos.beleza.adapter.database.h2.servico;

import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ServicoRepository;
import br.com.fiap.servicos.beleza.usecase.database.profissional.IDeletarProfissional;
import br.com.fiap.servicos.beleza.usecase.database.servico.IDeletarServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarServico implements IDeletarServico {

    private final ServicoRepository servicoRepository;

    @Override
    @Transactional
    public void deletarServico(long servicoId) {
        servicoRepository.deleteById(servicoId);
    }
}