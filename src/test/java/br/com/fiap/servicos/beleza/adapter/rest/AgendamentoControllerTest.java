package br.com.fiap.servicos.beleza.adapter.rest;


import br.com.fiap.servicos.beleza.adapter.rest.controller.AgendamentoController;
import br.com.fiap.servicos.beleza.adapter.rest.dto.agendamento.AgendamentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.agendamento.AgendamentoResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AgendamentoControllerTest {

    @Mock
    private ICadastrarAgendamento cadastrarAgendamento;

    @Mock
    private IBuscarAgendamento buscarAgendamento;

    @Mock
    private IAtualizarAgendamento atualizarAgendamento;

    @Mock
    private IDeletarAgendamento deletarAgendamento;

    @InjectMocks
    private AgendamentoController agendamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarAgendamento() {
        AgendamentoRequestDto requestDto = new AgendamentoRequestDto(
                LocalDateTime.now(), 1L, 2L, 3L
        );

        AgendamentoResponseDB responseDB = AgendamentoResponseDB.builder()
                .agendamentoId(1L)
                .dataHora(requestDto.dataHora())
                .clienteId(requestDto.clienteId())
                .profissionalId(requestDto.profissionalId())
                .servicoId(requestDto.servicoId())
                .build();

        when(cadastrarAgendamento.execute(any())).thenReturn(responseDB);

        ResponseEntity<AgendamentoResponseDto> response = agendamentoController.cadastrarAgendamento(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDB.agendamentoId(), response.getBody().agendamentoId());
        verify(cadastrarAgendamento, times(1)).execute(any());
    }

    @Test
    void testBuscarAgendamento() {
        long agendamentoId = 1L;
        AgendamentoResponseDB responseDB = AgendamentoResponseDB.builder()
                .agendamentoId(agendamentoId)
                .dataHora(LocalDateTime.now())
                .clienteId(1L)
                .profissionalId(2L)
                .servicoId(3L)
                .build();

        when(buscarAgendamento.buscarAgendamento(agendamentoId)).thenReturn(responseDB);

        ResponseEntity<AgendamentoResponseDto> response = agendamentoController.buscarAgendamento(agendamentoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDB.agendamentoId(), response.getBody().agendamentoId());
        verify(buscarAgendamento, times(1)).buscarAgendamento(agendamentoId);
    }

    @Test
    void testBuscarAgendamentoNotFound() {
        long agendamentoId = 1L;

        when(buscarAgendamento.buscarAgendamento(agendamentoId)).thenReturn(null);

        ResponseEntity<AgendamentoResponseDto> response = agendamentoController.buscarAgendamento(agendamentoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(buscarAgendamento, times(1)).buscarAgendamento(agendamentoId);
    }

    @Test
    void testAtualizarAgendamento() {
        long agendamentoId = 1L;
        AgendamentoRequestDto requestDto = new AgendamentoRequestDto(
                LocalDateTime.now(), 1L, 2L, 3L
        );

        AgendamentoResponseDB responseDB = AgendamentoResponseDB.builder()
                .agendamentoId(agendamentoId)
                .dataHora(requestDto.dataHora())
                .clienteId(requestDto.clienteId())
                .profissionalId(requestDto.profissionalId())
                .servicoId(requestDto.servicoId())
                .build();

        when(atualizarAgendamento.atualizarAgendamento(eq(agendamentoId), any())).thenReturn(responseDB);

        ResponseEntity<AgendamentoResponseDto> response = agendamentoController.atualizarAgendamento(agendamentoId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDB.agendamentoId(), response.getBody().agendamentoId());
        verify(atualizarAgendamento, times(1)).atualizarAgendamento(eq(agendamentoId), any());
    }

    @Test
    void testDeletarAgendamento() {
        long agendamentoId = 1L;

        doNothing().when(deletarAgendamento).deletarAgendamento(agendamentoId);

        ResponseEntity<Void> response = agendamentoController.deletarAgendamento(agendamentoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletarAgendamento, times(1)).deletarAgendamento(agendamentoId);
    }
}
