package br.com.fiap.servicos.beleza.adapter.database.h2.avaliacao;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAvaliacaoEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.AvaliacaoEstabelecimentoRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.AvaliacaoEstabelecimentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CadastrarAvaliacaoEstabelecimentoTest {

    @Mock
    private AvaliacaoEstabelecimentoRepository avaliacaoEstabelecimentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private CadastrarAvaliacaoEstabelecimento cadastrarAvaliacaoEstabelecimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarAvaliacaoEstabelecimentoComSucesso() {
        long clienteId = 1L;
        long estabelecimentoId = 2L;
        int pontuacao = 5;
        String comentario = "Excelente serviço";

        AvaliacaoEstabelecimentoRequest request = AvaliacaoEstabelecimentoRequest.builder()
                .clienteId(clienteId)
                .estabelecimentoId(estabelecimentoId)
                .pontuacao(pontuacao)
                .comentario(comentario)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        TabelaEstabelecimento estabelecimento = new TabelaEstabelecimento();
        estabelecimento.setId(estabelecimentoId);

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
        when(estabelecimentoRepository.findById(estabelecimentoId)).thenReturn(java.util.Optional.of(estabelecimento));

        cadastrarAvaliacaoEstabelecimento.cadastrarAvaliacaoEstabelecimento(request);

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(estabelecimentoRepository, times(1)).findById(estabelecimentoId);
        verify(avaliacaoEstabelecimentoRepository, times(1)).save(any(TabelaAvaliacaoEstabelecimento.class));
    }

    @Test
    void cadastrarAvaliacaoEstabelecimentoClienteNaoEncontrado() {
        long clienteId = 1L;
        long estabelecimentoId = 2L;
        int pontuacao = 5;
        String comentario = "Excelente serviço";

        AvaliacaoEstabelecimentoRequest request = AvaliacaoEstabelecimentoRequest.builder()
                .clienteId(clienteId)
                .estabelecimentoId(estabelecimentoId)
                .pontuacao(pontuacao)
                .comentario(comentario)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            cadastrarAvaliacaoEstabelecimento.cadastrarAvaliacaoEstabelecimento(request);
        });

        assertEquals("Cliente nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(estabelecimentoRepository, never()).findById(anyLong());
        verify(avaliacaoEstabelecimentoRepository, never()).save(any());
    }

    @Test
    void cadastrarAvaliacaoEstabelecimentoEstabelecimentoNaoEncontrado() {
        long clienteId = 1L;
        long estabelecimentoId = 2L;
        int pontuacao = 5;
        String comentario = "Excelente serviço";

        AvaliacaoEstabelecimentoRequest request = AvaliacaoEstabelecimentoRequest.builder()
                .clienteId(clienteId)
                .estabelecimentoId(estabelecimentoId)
                .pontuacao(pontuacao)
                .comentario(comentario)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
        when(estabelecimentoRepository.findById(estabelecimentoId)).thenReturn(java.util.Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            cadastrarAvaliacaoEstabelecimento.cadastrarAvaliacaoEstabelecimento(request);
        });

        assertEquals("Estabelecimento nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(estabelecimentoRepository, times(1)).findById(estabelecimentoId);
        verify(avaliacaoEstabelecimentoRepository, never()).save(any());
    }
}
