package br.com.fiap.servicos.beleza.adapter.database.h2.agendamento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAgendamento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import br.com.fiap.servicos.beleza.adapter.database.repository.AgendamentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.AgendamentoResponseDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarAgendamentoTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private BuscarAgendamento buscarAgendamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarAgendamentoComSucesso() {
        long agendamentoId = 1L;

        TabelaAgendamento agendamento = new TabelaAgendamento();
        agendamento.setId(agendamentoId);
        agendamento.setDataHora(LocalDateTime.now());

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(2L);
        agendamento.setCliente(cliente);

        TabelaProfissional profissional = new TabelaProfissional();
        profissional.setId(3L);
        agendamento.setProfissional(profissional);

        TabelaServico servico = new TabelaServico();
        servico.setId(4L);
        agendamento.setServico(servico);

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));

        AgendamentoResponseDB response = buscarAgendamento.buscarAgendamento(agendamentoId);

        assertNotNull(response);
        assertEquals(agendamentoId, response.agendamentoId());
        assertEquals(agendamento.getDataHora(), response.dataHora());
        assertEquals(cliente.getId(), response.clienteId());
        assertEquals(profissional.getId(), response.profissionalId());
        assertEquals(servico.getId(), response.servicoId());

        verify(agendamentoRepository, times(1)).findById(agendamentoId);
    }

    @Test
    void buscarAgendamentoNaoEncontrado() {
        long agendamentoId = 1L;

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                buscarAgendamento.buscarAgendamento(agendamentoId)
        );

        assertEquals("Error", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(agendamentoId);
    }
}
