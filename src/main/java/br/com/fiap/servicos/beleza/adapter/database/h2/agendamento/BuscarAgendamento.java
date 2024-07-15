package br.com.fiap.servicos.beleza.adapter.database.h2.agendamento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAgendamento;
import br.com.fiap.servicos.beleza.adapter.database.repository.AgendamentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.AgendamentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.IBuscarAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarAgendamento implements IBuscarAgendamento {

    private final AgendamentoRepository agendamentoRepository;

    @Override
    @Transactional
    public AgendamentoResponseDB buscarAgendamento(long agendamentoId) {
        var agendamento = agendamentoRepository.findById(agendamentoId);
        var agendamentoValidado = validarAgendamento(agendamento);
        return converterResponseDatabaseParaUC(agendamentoValidado);
    }

    private Optional<TabelaAgendamento> validarAgendamento(Optional<TabelaAgendamento> response) {
        if (!response.isPresent())
            throw new RuntimeException("Error");
        return response;
    }

    private AgendamentoResponseDB converterResponseDatabaseParaUC(Optional<TabelaAgendamento> response) {
        return AgendamentoResponseDB.builder()
                .agendamentoId(response.get().getId())
                .dataHora(response.get().getDataHora())
                .clienteId(response.get().getCliente().getId())
                .profissionalId(response.get().getProfissional().getId())
                .servicoId(response.get().getServico().getId())
                .build();
    }
}
