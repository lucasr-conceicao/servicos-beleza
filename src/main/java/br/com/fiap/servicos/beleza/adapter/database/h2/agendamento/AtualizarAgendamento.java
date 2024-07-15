package br.com.fiap.servicos.beleza.adapter.database.h2.agendamento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAgendamento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import br.com.fiap.servicos.beleza.adapter.database.repository.AgendamentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ServicoRepository;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.AgendamentoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.AgendamentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.IAtualizarAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarAgendamento implements IAtualizarAgendamento {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;

    @Override
    @Transactional
    public AgendamentoResponseDB atualizarAgendamento(long agendamentoId, AgendamentoRequestDB requestDB) {
        var agendamento = buscarAgendamento(agendamentoId);
        var cliente = buscarCliente(requestDB.clienteId());
        var profissional = buscarProfissional(requestDB.profissionalId());
        var servico = buscarServico(requestDB.servicoId());

        agendamento.setDataHora(requestDB.dataHora());
        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        agendamentoRepository.save(agendamento);
        return AgendamentoResponseDB.builder()
                .agendamentoId(agendamento.getId())
                .dataHora(agendamento.getDataHora())
                .clienteId(agendamento.getCliente().getId())
                .profissionalId(agendamento.getProfissional().getId())
                .servicoId(agendamento.getServico().getId())
                .build();
    }

    @Transactional(readOnly = true)
    private TabelaAgendamento buscarAgendamento(long agendamentoId) {
        return agendamentoRepository.findById(agendamentoId).orElseThrow(
                () -> new RuntimeException("Agendamento não encontrado"));
    }

    @Transactional(readOnly = true)
    private TabelaCliente buscarCliente(long clientId) {
        return  clienteRepository.findById(clientId).orElseThrow(
                () -> new RuntimeException(""));
    }


    @Transactional(readOnly = true)
    private TabelaProfissional buscarProfissional(long profissionalId) {
        return profissionalRepository.findById(profissionalId).orElseThrow(
                () -> new RuntimeException(""));
    }

    @Transactional(readOnly = true)
    private TabelaServico buscarServico(long servicoId) {
        return servicoRepository.findById(servicoId).orElseThrow(
                () -> new RuntimeException(""));
    }
}
