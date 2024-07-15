package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DeletarEstabelecimentoTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private DeletarEstabelecimento deletarEstabelecimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletarEstabelecimento() {
        long estabelecimentoId = 1L;

        TabelaEstabelecimento estabelecimento = TabelaEstabelecimento.builder()
                .id(estabelecimentoId)
                .nome("Salão Beleza Total")
                .endereco("Rua das Flores, 123")
                .horariosFuncionamento("Segunda a Sexta, das 09:00 às 18:00")
                .build();

        when(estabelecimentoRepository.findById(anyLong())).thenReturn(Optional.of(estabelecimento));

        deletarEstabelecimento.deletarEstabelecimento(estabelecimentoId);

        verify(estabelecimentoRepository, times(1)).findById(estabelecimentoId);
        verify(estabelecimentoRepository, times(1)).delete(estabelecimento);
    }

    @Test
    void deletarEstabelecimento_NotFound() {
        long estabelecimentoId = 1L;

        when(estabelecimentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            deletarEstabelecimento.deletarEstabelecimento(estabelecimentoId);
        } catch (NotFoundException e) {
            // Verifica se foi lançada a exceção correta
            verify(estabelecimentoRepository, times(1)).findById(estabelecimentoId);
            verify(estabelecimentoRepository, never()).delete(any());
            return;
        }

        // Se não lançou NotFoundException, falha o teste
        throw new AssertionError("Expected NotFoundException was not thrown");
    }
}

