package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AtualizarEstabelecimentoTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private AtualizarEstabelecimento atualizarEstabelecimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void atualizarEstabelecimento() {
        long estabelecimentoId = 1L;
        String nomeEstabelecimento = "Salão Beleza Total";
        String endereco = "Rua das Flores, 123";
        String horarioFuncionamento = "Segunda a Sexta, das 09:00 às 18:00";

        EstabelecimentoRequestDB request = EstabelecimentoRequestDB.builder()
                .nomeEstabelecimento(nomeEstabelecimento)
                .endereco(endereco)
                .horarioFuncionamento(horarioFuncionamento)
                .build();

        TabelaEstabelecimento estabelecimentoAtualizado = TabelaEstabelecimento.builder()
                .id(estabelecimentoId)
                .nome(nomeEstabelecimento)
                .endereco(endereco)
                .horariosFuncionamento(horarioFuncionamento)
                .build();

        when(estabelecimentoRepository.findById(estabelecimentoId)).thenReturn(java.util.Optional.of(estabelecimentoAtualizado));
        when(estabelecimentoRepository.save(any(TabelaEstabelecimento.class))).thenReturn(estabelecimentoAtualizado);

        EstabelecimentoResponseDB response = atualizarEstabelecimento.atualizarEstabelecimento(estabelecimentoId, request);

        assertEquals(estabelecimentoId, response.estabelecimentoId());
        assertEquals(nomeEstabelecimento, response.nomeEstabelecimento());
        assertEquals(endereco, response.endereco());
        assertEquals(horarioFuncionamento, response.horarioFuncionamento());

        verify(estabelecimentoRepository, times(1)).findById(estabelecimentoId);
        verify(estabelecimentoRepository, times(1)).save(any(TabelaEstabelecimento.class));
    }
}

