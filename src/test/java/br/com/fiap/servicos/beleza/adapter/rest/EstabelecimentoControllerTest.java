package br.com.fiap.servicos.beleza.adapter.rest;

import br.com.fiap.servicos.beleza.adapter.rest.controller.EstabelecimentoController;
import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EstabelecimentoControllerTest {

    @Mock
    private ICadastrarEstabelecimento cadastrarEstabelecimento;

    @Mock
    private IBuscarEstabelecimento buscarEstabelecimento;

    @Mock
    private IAtualizarEstabelecimento atualizarEstabelecimento;

    @Mock
    private IDeletarEstabelecimento deletarEstabelecimento;

    @InjectMocks
    private EstabelecimentoController estabelecimentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarEstabelecimento() {
        EstabelecimentoRequestDto requestDto = new EstabelecimentoRequestDto(
                "Salão Beleza Pura",
                "Rua das Flores, 123, Centro, Cidade XYZ",
                "Segunda a Sexta, das 09:00 às 19:00"
        );

        EstabelecimentoResponseDB responseDB = EstabelecimentoResponseDB.builder()
                .estabelecimentoId(1L)
                .nomeEstabelecimento(requestDto.nomeEstabelecimento())
                .endereco(requestDto.endereco())
                .horarioFuncionamento(requestDto.horarioFuncionamento())
                .build();

        when(cadastrarEstabelecimento.execute(any())).thenReturn(responseDB);

        ResponseEntity<EstabelecimentoResponseDto> response = estabelecimentoController.cadastrarEstabelecimento(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDB.estabelecimentoId(), response.getBody().estabelecimentoId());
        verify(cadastrarEstabelecimento, times(1)).execute(any());
    }


    @Test
    void testDeletarEstabelecimento() {
        long estabelecimentoId = 1L;

        doNothing().when(deletarEstabelecimento).deletarEstabelecimento(estabelecimentoId);

        ResponseEntity<Void> response = estabelecimentoController.deletarEstabelecimento(estabelecimentoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletarEstabelecimento, times(1)).deletarEstabelecimento(estabelecimentoId);
    }
}
