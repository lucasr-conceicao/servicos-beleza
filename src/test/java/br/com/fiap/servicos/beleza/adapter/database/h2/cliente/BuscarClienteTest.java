package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteResponseDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BuscarClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private BuscarCliente buscarCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarClienteExistente() {
        long clienteId = 1L;
        String nomeCliente = "João";
        String emailCliente = "joao@example.com";

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);
        cliente.setNome(nomeCliente);
        cliente.setEmail(emailCliente);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        ClienteResponseDB response = buscarCliente.buscarCliente(clienteId);

        assertEquals(clienteId, response.clienteId());
        assertEquals(nomeCliente, response.nome());
        assertEquals(emailCliente, response.email());

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void buscarClienteNaoExistente() {
        long clienteId = 1L;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            buscarCliente.buscarCliente(clienteId);
        });

        assertEquals("Cliente não encontrado.", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
    }
}
