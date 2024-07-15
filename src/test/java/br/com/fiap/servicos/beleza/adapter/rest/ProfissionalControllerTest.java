package br.com.fiap.servicos.beleza.adapter.rest;

import br.com.fiap.servicos.beleza.adapter.rest.controller.ProfissionalController;
import br.com.fiap.servicos.beleza.adapter.rest.dto.profissional.ProfissionalRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.profissional.ProfissionalResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.profissional.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfissionalControllerTest {

    @Mock
    private ICadastrarProfissional cadastrarProfissional;

    @Mock
    private IBuscarProfissional buscarProfissional;

    @Mock
    private IAtualizarProfissional atualizarProfissional;

    @Mock
    private IDeletarProfissional deletarProfissional;

    @InjectMocks
    private ProfissionalController profissionalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarProfissional() {
        ProfissionalRequestDto requestDto = new ProfissionalRequestDto(
                "Maria Silva",
                List.of("Cabeleireiro", "Colorista").toString(),
                50.0,
                List.of("09:00-10:00", "14:00-15:00").toString(),
                1L
        );

        ProfissionalResponseDB responseDB = ProfissionalResponseDB.builder()
                .profissionalId(1L)
                .nome(requestDto.nome())
                .especialidades(requestDto.especialidade())
                .tarifa(requestDto.tarifa())
                .horariosDisponiveis(requestDto.horariosDisponiveis())
                .estabelecimentoId(requestDto.estabelecimento())
                .build();

        when(cadastrarProfissional.execute(any())).thenReturn(responseDB);

        ResponseEntity<ProfissionalResponseDto> response = profissionalController.cadastrarProfissional(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDB.profissionalId(), response.getBody().profissionalId());
        verify(cadastrarProfissional, times(1)).execute(any());
    }

    @Test
    void testBuscarProfissional() {
        long profissionalId = 1L;

        ProfissionalResponseDB responseDB = ProfissionalResponseDB.builder()
                .profissionalId(profissionalId)
                .nome("Maria Silva")
                .especialidades(List.of("Cabeleireiro", "Colorista").toString())
                .tarifa(50.0)
                .horariosDisponiveis(List.of("09:00-10:00", "14:00-15:00").toString())
                .estabelecimentoId(1L)
                .build();

        when(buscarProfissional.buscarProfissional(profissionalId)).thenReturn(responseDB);

        ResponseEntity<ProfissionalResponseDto> response = profissionalController.buscarProfissional(profissionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDB.profissionalId(), response.getBody().profissionalId());
        verify(buscarProfissional, times(1)).buscarProfissional(profissionalId);
    }

    @Test
    void testAtualizarProfissional() {
        long profissionalId = 1L;
        ProfissionalRequestDto requestDto = new ProfissionalRequestDto(
                "Maria Silva",
                List.of("Cabeleireiro", "Colorista").toString(),
                50.0,
                List.of("09:00-10:00", "14:00-15:00").toString(),
                1L
        );

        ProfissionalResponseDB responseDB = ProfissionalResponseDB.builder()
                .profissionalId(profissionalId)
                .nome(requestDto.nome())
                .especialidades(requestDto.especialidade())
                .tarifa(requestDto.tarifa())
                .horariosDisponiveis(requestDto.horariosDisponiveis())
                .estabelecimentoId(requestDto.estabelecimento())
                .build();

        when(atualizarProfissional.atualizarProfissional(eq(profissionalId), any())).thenReturn(responseDB);

        ResponseEntity<ProfissionalResponseDto> response = profissionalController.atualizarProfissional(profissionalId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDB.profissionalId(), response.getBody().profissionalId());
        verify(atualizarProfissional, times(1)).atualizarProfissional(eq(profissionalId), any());
    }

    @Test
    void testDeletarProfissional() {
        long profissionalId = 1L;

        doNothing().when(deletarProfissional).deletarProfissional(profissionalId);

        ResponseEntity<Void> response = profissionalController.deletarProfissional(profissionalId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletarProfissional, times(1)).deletarProfissional(profissionalId);
    }
}
