package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;


import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseProfissionaisDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoServicoResponseDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BuscarEstabelecimentoTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private BuscarEstabelecimento buscarEstabelecimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarEstabelecimento() {
        long estabelecimentoId = 1L;
        String nomeEstabelecimento = "Salão Beleza Total";
        String endereco = "Rua das Flores, 123";
        String horarioFuncionamento = "Segunda a Sexta, das 09:00 às 18:00";

        TabelaEstabelecimento estabelecimento = TabelaEstabelecimento.builder()
                .id(estabelecimentoId)
                .nome(nomeEstabelecimento)
                .endereco(endereco)
                .horariosFuncionamento(horarioFuncionamento)
                .servicos(Collections.singletonList(TabelaServico.builder()
                        .id(1L)
                        .nome("Corte de Cabelo")
                        .descricao("Corte de cabelo masculino e feminino.")
                        .valor(BigDecimal.valueOf(50.0))
                        .build()))
                .profissionais(Collections.singletonList(TabelaProfissional.builder()
                        .id(1L)
                        .nome("João Silva")
                        .especialidades(Collections.singletonList("Cabeleireiro").toString())
                        .tarifa(50.0)
                        .build()))
                .build();

        when(estabelecimentoRepository.findById(anyLong())).thenReturn(Optional.of(estabelecimento));

        EstabelecimentoResponseDB response = buscarEstabelecimento.buscarEstabelecimento(estabelecimentoId);

        assertEquals(estabelecimentoId, response.estabelecimentoId());
        assertEquals(nomeEstabelecimento, response.nomeEstabelecimento());
        assertEquals(endereco, response.endereco());
        assertEquals(horarioFuncionamento, response.horarioFuncionamento());
        assertEquals(1, response.servicos().size());
        assertEquals(1, response.profissionais().size());

        verify(estabelecimentoRepository, times(1)).findById(estabelecimentoId);
    }
}
