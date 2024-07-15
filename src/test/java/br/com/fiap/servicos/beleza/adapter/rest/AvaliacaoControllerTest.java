package br.com.fiap.servicos.beleza.adapter.rest;

import br.com.fiap.servicos.beleza.adapter.rest.controller.AvaliacaoController;
import br.com.fiap.servicos.beleza.adapter.rest.dto.avaliacao.AvaliacaoEstabelecimentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.avaliacao.AvaliacaoProfissionalRequestDto;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.ICadastrarAvaliacaoEstabelecimento;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.ICadastrarAvaliacaoProfissional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvaliacaoControllerTest {

    @Mock
    private ICadastrarAvaliacaoEstabelecimento cadastrarAvaliacaoEstabelecimento;

    @Mock
    private ICadastrarAvaliacaoProfissional cadastrarAvaliacaoProfissional;

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    public AvaliacaoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAvaliarEstabelecimento() {
        AvaliacaoEstabelecimentoRequestDto requestDto = new AvaliacaoEstabelecimentoRequestDto(
                1L, 10L, 20L, 5, "Ótimo serviço"
        );

        ResponseEntity<Void> response = avaliacaoController.avaliarEstabelecimento(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(cadastrarAvaliacaoEstabelecimento, times(1)).cadastrarAvaliacaoEstabelecimento(any());
    }

    @Test
    void testAvaliarProfissional() {
        AvaliacaoProfissionalRequestDto requestDto = new AvaliacaoProfissionalRequestDto(
                1L, 30L, 40L, 4, "Excelente profissional"
        );

        ResponseEntity<Void> response = avaliacaoController.avaliarProfissional(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(cadastrarAvaliacaoProfissional, times(1)).cadastrarAvaliacaoProfissional(any());
    }
}
