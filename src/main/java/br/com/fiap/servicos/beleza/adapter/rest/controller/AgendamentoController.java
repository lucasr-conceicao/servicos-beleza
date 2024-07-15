package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.agendamento.AgendamentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.agendamento.AgendamentoResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.agendamento.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class AgendamentoController {

    private final ICadastrarAgendamento cadastrarAgendamento;
    private final IBuscarAgendamento buscarAgendamento;
    private final IAtualizarAgendamento atualizarAgendamento;
    private final IDeletarAgendamento deletarAgendamento;


    @PostMapping("/agendamento")
    public ResponseEntity<AgendamentoResponseDto> cadastrarAgendamento(@RequestBody AgendamentoRequestDto requestDto) {
        var response = cadastrarAgendamento.execute(montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(converterResponseUCParaDto(response));
    }

    @GetMapping("/agendamento/{agendamentoId}")
    public ResponseEntity<AgendamentoResponseDto> buscarAgendamento(@PathVariable(value = "agendamentoId") long agendamentoId) {
        var response = buscarAgendamento.buscarAgendamento(agendamentoId);
        return response != null ? ResponseEntity.ok(converterResponseUCParaDto(response)) : ResponseEntity.notFound().build();
    }

    @PutMapping("/agendamento/{agendamentoId}")
    public ResponseEntity<AgendamentoResponseDto> atualizarAgendamento(@PathVariable long agendamentoId, @RequestBody AgendamentoRequestDto requestDto) {
        var response = atualizarAgendamento.atualizarAgendamento(agendamentoId, montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @DeleteMapping("/agendamento/{agendamentoId}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable long agendamentoId) {
        deletarAgendamento.deletarAgendamento(agendamentoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private AgendamentoRequestDB montarRequest(AgendamentoRequestDto dto) {
        return AgendamentoRequestDB.builder()
                .dataHora(dto.dataHora())
                .clienteId(dto.clienteId())
                .profissionalId(dto.profissionalId())
                .servicoId(dto.servicoId())
                .build();
    }

    private AgendamentoResponseDto converterResponseUCParaDto(AgendamentoResponseDB responseDB) {
        return AgendamentoResponseDto.builder()
                .agendamentoId(responseDB.agendamentoId())
                .dataHora(responseDB.dataHora())
                .clienteId(responseDB.clienteId())
                .profissionalId(responseDB.profissionalId())
                .servicoId(responseDB.servicoId())
                .build();
    }
}
