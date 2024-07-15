package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.horario.HorarioRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.horario.HorarioResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.horario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class HorarioController {

    private final ICadastrarHorario cadastrarHorario;
    private final IBuscarHorario buscarHorario;
    private final IAtualizarHorario atualizarHorario;
    private final IDeletarHorario deletarHorario;


    @PostMapping("/horario")
    public ResponseEntity<HorarioResponseDto> cadastrarHorario(@RequestBody HorarioRequestDto requestDto) {
        var response = cadastrarHorario.execute(montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(converterResponseUCParaDto(response));
    }

    @GetMapping("/horario/{horarioId}")
    public ResponseEntity<HorarioResponseDto> buscarHorario(@PathVariable(value = "horarioId") long horarioId) {
        var response = buscarHorario.buscarHorario(horarioId);
        return response != null ? ResponseEntity.ok(converterResponseUCParaDto(response)) : ResponseEntity.notFound().build();
    }

    @PutMapping("/horario/{horarioId}")
    public ResponseEntity<HorarioResponseDto> atualizarHorario(@PathVariable long horarioId, @RequestBody HorarioRequestDto requestDto) {
        var response = atualizarHorario.atualizarHorario(horarioId, montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @DeleteMapping("/horario/{horarioId}")
    public ResponseEntity<Void> deletarHorario(@PathVariable long horarioId) {
        deletarHorario.deletarHorario(horarioId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private HorarioRequestDB montarRequest(HorarioRequestDto dto) {
        return HorarioRequestDB.builder()
                .data(dto.data())
                .horaInicio(dto.horaInicio())
                .horaFim(dto.horaFim())
                .profissionalId(dto.profissionalId())
                .clienteId(dto.clienteId())
                .build();
    }

    private HorarioResponseDto converterResponseUCParaDto(HorarioResponseDB responseDB) {
        return HorarioResponseDto.builder()
                .horarioId(responseDB.horarioId())
                .data(responseDB.data())
                .horaInicio(responseDB.horaInicio())
                .horaFim(responseDB.horaFim())
                .profissionalId(responseDB.profissionalId())
                .clienteId(responseDB.clienteId())
                .build();
    }
}
