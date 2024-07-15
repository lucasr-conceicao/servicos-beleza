package br.com.fiap.servicos.beleza.adapter.database.h2.agendamento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAgendamento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.AgendamentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ServicoRepository;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.AgendamentoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.AgendamentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.ICadastrarAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarAgendamento implements ICadastrarAgendamento {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;

    @Transactional
    @Override
    public AgendamentoResponseDB execute(AgendamentoRequestDB request) {
        var cliente = buscarCliente(request.clienteId());
        var profissional = buscarProfissional(request.profissionalId());
        var servico = buscarServico(request.servicoId());

        var agendamento = montarAgendamentoRequest(request, cliente, profissional, servico);
        agendamentoRepository.save(agendamento);
        return converterResponseDatabaseParaUC(agendamento);
    }

    private TabelaAgendamento montarAgendamentoRequest(AgendamentoRequestDB requestDB, TabelaCliente cliente, TabelaProfissional profissional, TabelaServico servico) {
        return TabelaAgendamento.builder()
                .dataHora(requestDB.dataHora())
                .cliente(cliente)
                .profissional(profissional)
                .servico(servico)
                .build();
    }

    private AgendamentoResponseDB converterResponseDatabaseParaUC(TabelaAgendamento tabela) {
        return AgendamentoResponseDB.builder()
                .agendamentoId(tabela.getId())
                .dataHora(tabela.getDataHora())
                .clienteId(tabela.getCliente().getId())
                .profissionalId(tabela.getProfissional().getId())
                .servicoId(tabela.getServico().getId())
                .build();
    }

    @Transactional(readOnly = true)
    private TabelaCliente buscarCliente(long clientId) {
        return  clienteRepository.findById(clientId).orElseThrow(
                () -> new NotFoundException("Cliente nao encontrado"));
    }

    @Transactional(readOnly = true)
    private TabelaProfissional buscarProfissional(long profissionalId) {
        return profissionalRepository.findById(profissionalId).orElseThrow(
                () -> new NotFoundException("Profissional nao encontrado"));
    }

    @Transactional(readOnly = true)
    private TabelaServico buscarServico(long servicoId) {
        return servicoRepository.findById(servicoId).orElseThrow(
                () -> new NotFoundException("Servico nao encontrado"));
    }
}
