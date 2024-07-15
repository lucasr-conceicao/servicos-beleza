package br.com.fiap.servicos.beleza.adapter.rest;

import br.com.fiap.servicos.beleza.adapter.rest.controller.ServicoController;
import br.com.fiap.servicos.beleza.adapter.rest.dto.servico.ServicoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.servico.ServicoResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.servico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicoControllerTest {

    @Mock
    private ICadastrarServico cadastrarServico;

    @Mock
    private IAtualizarServico atualizarServico;

    @Mock
    private IDeletarServico deletarServico;

    @InjectMocks
    private ServicoController servicoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarServico() {
        ServicoRequestDto requestDto = new ServicoRequestDto(
                "Corte de Cabelo",
                "Corte de cabelo masculino e feminino",
                50.0,
                1L
        );

        ServicoResponseDB responseDB = ServicoResponseDB.builder()
                .servicoId(1L)
                .nomeServico(requestDto.nomeServico())
                .descricaoServico(requestDto.descricaoServico())
                .valor(BigDecimal.valueOf(requestDto.valor()))
                .estabelecimentoId(requestDto.estabelecimentoId())
                .build();

        when(cadastrarServico.cadastrarServico(any())).thenReturn(responseDB);

        ResponseEntity<ServicoResponseDto> response = servicoController.cadastrarServico(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDB.servicoId(), response.getBody().servicoId());
        verify(cadastrarServico, times(1)).cadastrarServico(any());
    }

    @Test
    void testAtualizarServico() {
        long servicoId = 1L;
        ServicoRequestDto requestDto = new ServicoRequestDto(
                "Corte de Cabelo",
                "Corte de cabelo masculino e feminino",
                50.0,
                1L
        );

        ServicoResponseDB responseDB = ServicoResponseDB.builder()
                .servicoId(servicoId)
                .nomeServico(requestDto.nomeServico())
                .descricaoServico(requestDto.descricaoServico())
                .valor(BigDecimal.valueOf(requestDto.valor()))
                .estabelecimentoId(requestDto.estabelecimentoId())
                .build();

        when(atualizarServico.atualizarServico(eq(servicoId), any())).thenReturn(responseDB);

        ResponseEntity<ServicoResponseDto> response = servicoController.atualizarServico(servicoId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDB.servicoId(), response.getBody().servicoId());
        verify(atualizarServico, times(1)).atualizarServico(eq(servicoId), any());
    }

    @Test
    void testDeletarServico() {
        long servicoId = 1L;

        doNothing().when(deletarServico).deletarServico(servicoId);

        ResponseEntity<Void> response = servicoController.deletarServico(servicoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletarServico, times(1)).deletarServico(servicoId);
    }
}
