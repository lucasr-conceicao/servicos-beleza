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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarAgendamentoTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private CadastrarAgendamento cadastrarAgendamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarAgendamentoComSucesso() {
        long clienteId = 1L;
        long profissionalId = 2L;
        long servicoId = 3L;
        LocalDateTime dataHora = LocalDateTime.now();

        AgendamentoRequestDB request = AgendamentoRequestDB.builder()
                .clienteId(clienteId)
                .profissionalId(profissionalId)
                .servicoId(servicoId)
                .dataHora(dataHora)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        TabelaProfissional profissional = new TabelaProfissional();
        profissional.setId(profissionalId);

        TabelaServico servico = new TabelaServico();
        servico.setId(servicoId);

        TabelaAgendamento agendamento = TabelaAgendamento.builder()
                .dataHora(dataHora)
                .cliente(cliente)
                .profissional(profissional)
                .servico(servico)
                .build();
        agendamento.setId(4L);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.of(profissional));
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));
        when(agendamentoRepository.save(any(TabelaAgendamento.class))).thenAnswer(invocation -> {
            TabelaAgendamento savedAgendamento = invocation.getArgument(0);
            savedAgendamento.setId(4L); // Simulate the database setting the ID
            return savedAgendamento;
        });

        AgendamentoResponseDB response = cadastrarAgendamento.execute(request);

        assertNotNull(response);
        assertEquals(4L, response.agendamentoId());
        assertEquals(dataHora, response.dataHora());
        assertEquals(clienteId, response.clienteId());
        assertEquals(profissionalId, response.profissionalId());
        assertEquals(servicoId, response.servicoId());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(profissionalRepository, times(1)).findById(profissionalId);
        verify(servicoRepository, times(1)).findById(servicoId);
        verify(agendamentoRepository, times(1)).save(any(TabelaAgendamento.class));
    }


    @Test
    void cadastrarAgendamentoClienteNaoEncontrado() {
        long clienteId = 1L;

        AgendamentoRequestDB request = AgendamentoRequestDB.builder()
                .clienteId(clienteId)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                cadastrarAgendamento.execute(request)
        );

        assertEquals("Cliente nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verifyNoMoreInteractions(profissionalRepository, servicoRepository, agendamentoRepository);
    }

    @Test
    void cadastrarAgendamentoProfissionalNaoEncontrado() {
        long clienteId = 1L;
        long profissionalId = 2L;

        AgendamentoRequestDB request = AgendamentoRequestDB.builder()
                .clienteId(clienteId)
                .profissionalId(profissionalId)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                cadastrarAgendamento.execute(request)
        );

        assertEquals("Profissional nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(profissionalRepository, times(1)).findById(profissionalId);
        verifyNoMoreInteractions(servicoRepository, agendamentoRepository);
    }

    @Test
    void cadastrarAgendamentoServicoNaoEncontrado() {
        long clienteId = 1L;
        long profissionalId = 2L;
        long servicoId = 3L;

        AgendamentoRequestDB request = AgendamentoRequestDB.builder()
                .clienteId(clienteId)
                .profissionalId(profissionalId)
                .servicoId(servicoId)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        TabelaProfissional profissional = new TabelaProfissional();
        profissional.setId(profissionalId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.of(profissional));
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                cadastrarAgendamento.execute(request)
        );

        assertEquals("Servico nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(profissionalRepository, times(1)).findById(profissionalId);
        verify(servicoRepository, times(1)).findById(servicoId);
        verifyNoMoreInteractions(agendamentoRepository);
    }
}
