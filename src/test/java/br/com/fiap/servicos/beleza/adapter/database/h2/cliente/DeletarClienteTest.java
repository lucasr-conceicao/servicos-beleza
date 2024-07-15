package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DeletarClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private DeletarCliente deletarCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletarCliente() {
        long clienteId = 1L;

        deletarCliente.deletarCliente(clienteId);

        verify(clienteRepository, times(1)).deleteById(clienteId);
    }
}
