package br.com.fiap.servicos.beleza.adapter.database.h2.agendamento;

import br.com.fiap.servicos.beleza.adapter.database.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeletarAgendamentoTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private DeletarAgendamento deletarAgendamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletarAgendamentoComSucesso() {
        long agendamentoId = 1L;

        deletarAgendamento.deletarAgendamento(agendamentoId);

        verify(agendamentoRepository, times(1)).deleteById(agendamentoId);
    }
}
