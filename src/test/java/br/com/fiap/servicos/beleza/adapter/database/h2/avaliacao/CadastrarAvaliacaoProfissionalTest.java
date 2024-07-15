package br.com.fiap.servicos.beleza.adapter.database.h2.avaliacao;

import br.com.fiap.servicos.beleza.adapter.database.domain.*;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.AvaliacaoProfissionalRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.AvaliacaoProfissionalRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CadastrarAvaliacaoProfissionalTest {

    @Mock
    private AvaliacaoProfissionalRepository avaliacaoProfissionalRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private CadastrarAvaliacaoProfissional cadastrarAvaliacaoProfissional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarAvaliacaoProfissionalComSucesso() {
        long clienteId = 1L;
        long profissionalId = 2L;
        int pontuacao = 5;
        String comentario = "Excelente profissional";

        AvaliacaoProfissionalRequest request = AvaliacaoProfissionalRequest.builder()
                .clienteId(clienteId)
                .profissionalId(profissionalId)
                .pontuacao(pontuacao)
                .comentario(comentario)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        TabelaProfissional profissional = new TabelaProfissional();
        profissional.setId(profissionalId);

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
        when(profissionalRepository.findById(profissionalId)).thenReturn(java.util.Optional.of(profissional));

        cadastrarAvaliacaoProfissional.cadastrarAvaliacaoProfissional(request);

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(profissionalRepository, times(1)).findById(profissionalId);
        verify(avaliacaoProfissionalRepository, times(1)).save(any(TabelaAvaliacaoProfissional.class));
    }

    @Test
    void cadastrarAvaliacaoProfissionalClienteNaoEncontrado() {
        long clienteId = 1L;
        long profissionalId = 2L;
        int pontuacao = 5;
        String comentario = "Excelente profissional";

        AvaliacaoProfissionalRequest request = AvaliacaoProfissionalRequest.builder()
                .clienteId(clienteId)
                .profissionalId(profissionalId)
                .pontuacao(pontuacao)
                .comentario(comentario)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            cadastrarAvaliacaoProfissional.cadastrarAvaliacaoProfissional(request);
        });

        assertEquals("Cliente nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(profissionalRepository, never()).findById(anyLong());
        verify(avaliacaoProfissionalRepository, never()).save(any());
    }

    @Test
    void cadastrarAvaliacaoProfissionalProfissionalNaoEncontrado() {
        long clienteId = 1L;
        long profissionalId = 2L;
        int pontuacao = 5;
        String comentario = "Excelente profissional";

        AvaliacaoProfissionalRequest request = AvaliacaoProfissionalRequest.builder()
                .clienteId(clienteId)
                .profissionalId(profissionalId)
                .pontuacao(pontuacao)
                .comentario(comentario)
                .build();

        TabelaCliente cliente = new TabelaCliente();
        cliente.setId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
        when(profissionalRepository.findById(profissionalId)).thenReturn(java.util.Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            cadastrarAvaliacaoProfissional.cadastrarAvaliacaoProfissional(request);
        });

        assertEquals("Profissional nao encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(profissionalRepository, times(1)).findById(profissionalId);
        verify(avaliacaoProfissionalRepository, never()).save(any());
    }
}
