package br.com.fiap.servicos.beleza.adapter.database.h2.agendamento;

import br.com.fiap.servicos.beleza.adapter.database.repository.AgendamentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.IDeletarAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarAgendamento implements IDeletarAgendamento {

    private final AgendamentoRepository agendamentoRepository;

    @Override
    @Transactional
    public void deletarAgendamento(long agendamentoId) {
        agendamentoRepository.deleteById(agendamentoId);
    }
}
