package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteResponseDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AtualizarClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AtualizarCliente atualizarCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void atualizarCliente() {
        long clienteId = 1L;
        String nomeNovo = "Joana";
        String emailNovo = "joana@example.com";

        ClienteRequestDB request = ClienteRequestDB.builder()
                .nome(nomeNovo)
                .email(emailNovo)
                .build();

        TabelaCliente clienteExistente = new TabelaCliente();
        clienteExistente.setId(clienteId);
        clienteExistente.setNome("João");
        clienteExistente.setEmail("joao@example.com");

        TabelaCliente clienteAtualizado = new TabelaCliente();
        clienteAtualizado.setId(clienteId);
        clienteAtualizado.setNome(nomeNovo);
        clienteAtualizado.setEmail(emailNovo);

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(clienteExistente));
        when(clienteRepository.save(any(TabelaCliente.class))).thenReturn(clienteAtualizado);

        ClienteResponseDB response = atualizarCliente.atualizarCliente(clienteId, request);

        assertEquals(clienteId, response.clienteId());
        assertEquals(nomeNovo, response.nome());
        assertEquals(emailNovo, response.email());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(TabelaCliente.class));
    }
}
