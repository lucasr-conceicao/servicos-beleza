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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarAgendamentoTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private AtualizarAgendamento atualizarAgendamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void atualizarAgendamentoComSucesso() {
        long agendamentoId = 1L;
        AgendamentoRequestDB requestDB = AgendamentoRequestDB.builder()
                .clienteId(2L)
                .profissionalId(3L)
                .servicoId(4L)
                .dataHora(LocalDateTime.now())
                .build();

        TabelaAgendamento agendamento = new TabelaAgendamento();
        agendamento.setId(agendamentoId);

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(requestDB.clienteId());

        TabelaProfissional profissional = new TabelaProfissional();
        profissional.setId(requestDB.profissionalId());

        TabelaServico servico = new TabelaServico();
        servico.setId(requestDB.servicoId());

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));
        when(clienteRepository.findById(requestDB.clienteId())).thenReturn(Optional.of(cliente));
        when(profissionalRepository.findById(requestDB.profissionalId())).thenReturn(Optional.of(profissional));
        when(servicoRepository.findById(requestDB.servicoId())).thenReturn(Optional.of(servico));

        AgendamentoResponseDB response = atualizarAgendamento.atualizarAgendamento(agendamentoId, requestDB);

        assertNotNull(response);
        assertEquals(agendamentoId, response.agendamentoId());
        assertEquals(requestDB.dataHora(), response.dataHora());
        assertEquals(requestDB.clienteId(), response.clienteId());
        assertEquals(requestDB.profissionalId(), response.profissionalId());
        assertEquals(requestDB.servicoId(), response.servicoId());

        verify(agendamentoRepository, times(1)).findById(agendamentoId);
        verify(clienteRepository, times(1)).findById(requestDB.clienteId());
        verify(profissionalRepository, times(1)).findById(requestDB.profissionalId());
        verify(servicoRepository, times(1)).findById(requestDB.servicoId());
        verify(agendamentoRepository, times(1)).save(agendamento);
    }

    @Test
    void atualizarAgendamentoNaoEncontrado() {
        long agendamentoId = 1L;
        AgendamentoRequestDB requestDB = AgendamentoRequestDB.builder()
                .clienteId(2L)
                .profissionalId(3L)
                .servicoId(4L)
                .dataHora(LocalDateTime.now())
                .build();

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizarAgendamento.atualizarAgendamento(agendamentoId, requestDB)
        );

        assertEquals("Agendamento não encontrado", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(agendamentoId);
        verify(clienteRepository, never()).findById(anyLong());
        verify(profissionalRepository, never()).findById(anyLong());
        verify(servicoRepository, never()).findById(anyLong());
        verify(agendamentoRepository, never()).save(any(TabelaAgendamento.class));
    }

    @Test
    void atualizarAgendamentoClienteNaoEncontrado() {
        long agendamentoId = 1L;
        AgendamentoRequestDB requestDB = AgendamentoRequestDB.builder()
                .clienteId(2L)
                .profissionalId(3L)
                .servicoId(4L)
                .dataHora(LocalDateTime.now())
                .build();

        TabelaAgendamento agendamento = new TabelaAgendamento();
        agendamento.setId(agendamentoId);

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));
        when(clienteRepository.findById(requestDB.clienteId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizarAgendamento.atualizarAgendamento(agendamentoId, requestDB)
        );

        assertEquals("", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(agendamentoId);
        verify(clienteRepository, times(1)).findById(requestDB.clienteId());
        verify(profissionalRepository, never()).findById(anyLong());
        verify(servicoRepository, never()).findById(anyLong());
        verify(agendamentoRepository, never()).save(any(TabelaAgendamento.class));
    }

    @Test
    void atualizarAgendamentoProfissionalNaoEncontrado() {
        long agendamentoId = 1L;
        AgendamentoRequestDB requestDB = AgendamentoRequestDB.builder()
                .clienteId(2L)
                .profissionalId(3L)
                .servicoId(4L)
                .dataHora(LocalDateTime.now())
                .build();

        TabelaAgendamento agendamento = new TabelaAgendamento();
        agendamento.setId(agendamentoId);

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(requestDB.clienteId());

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));
        when(clienteRepository.findById(requestDB.clienteId())).thenReturn(Optional.of(cliente));
        when(profissionalRepository.findById(requestDB.profissionalId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizarAgendamento.atualizarAgendamento(agendamentoId, requestDB)
        );

        assertEquals("", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(agendamentoId);
        verify(clienteRepository, times(1)).findById(requestDB.clienteId());
        verify(profissionalRepository, times(1)).findById(requestDB.profissionalId());
        verify(servicoRepository, never()).findById(anyLong());
        verify(agendamentoRepository, never()).save(any(TabelaAgendamento.class));
    }

    @Test
    void atualizarAgendamentoServicoNaoEncontrado() {
        long agendamentoId = 1L;
        AgendamentoRequestDB requestDB = AgendamentoRequestDB.builder()
                .clienteId(2L)
                .profissionalId(3L)
                .servicoId(4L)
                .dataHora(LocalDateTime.now())
                .build();

        TabelaAgendamento agendamento = new TabelaAgendamento();
        agendamento.setId(agendamentoId);

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(requestDB.clienteId());

        TabelaProfissional profissional = new TabelaProfissional();
        profissional.setId(requestDB.profissionalId());

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));
        when(clienteRepository.findById(requestDB.clienteId())).thenReturn(Optional.of(cliente));
        when(profissionalRepository.findById(requestDB.profissionalId())).thenReturn(Optional.of(profissional));
        when(servicoRepository.findById(requestDB.servicoId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizarAgendamento.atualizarAgendamento(agendamentoId, requestDB)
        );

        assertEquals("", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(agendamentoId);
        verify(clienteRepository, times(1)).findById(requestDB.clienteId());
        verify(profissionalRepository, times(1)).findById(requestDB.profissionalId());
        verify(servicoRepository, times(1)).findById(requestDB.servicoId());
        verify(agendamentoRepository, never()).save(any(TabelaAgendamento.class));
    }
}
