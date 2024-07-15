package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.profissional.ProfissionalRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.profissional.ProfissionalResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.profissional.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ICadastrarProfissional cadastrarProfissional;
    private final IBuscarProfissional buscarProfissional;
    private final IAtualizarProfissional atualizarProfissional;
    private final IDeletarProfissional deletarProfissional;

    @PostMapping("/profissional")
    public ResponseEntity<ProfissionalResponseDto> cadastrarProfissional(@RequestBody ProfissionalRequestDto requestDto) {
        var response = cadastrarProfissional.execute(montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(converterResponseUCParaDto(response));
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<ProfissionalResponseDto> buscarProfissional(@PathVariable(value = "profissionalId") long profissionalId) {
        var response = buscarProfissional.buscarProfissional(profissionalId);
        return response != null ? ResponseEntity.ok(converterResponseUCParaDto(response)) : ResponseEntity.notFound().build();
    }

    @PutMapping("/profissional/{profissionalId}")
    public ResponseEntity<ProfissionalResponseDto> atualizarProfissional(@PathVariable long profissionalId, @RequestBody ProfissionalRequestDto requestDto) {
        var response = atualizarProfissional.atualizarProfissional(profissionalId, montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @DeleteMapping("/profissional/{profissionalId}")
    public ResponseEntity<Void> deletarProfissional(@PathVariable long profissionalId) {
        deletarProfissional.deletarProfissional(profissionalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ProfissionalRequestDB montarRequest(ProfissionalRequestDto dto) {
        return ProfissionalRequestDB.builder()
                .nome(dto.nome())
                .especialidades(dto.especialidade())
                .tarifa(dto.tarifa())
                .horariosDisponiveis(dto.horariosDisponiveis())
                .estabelecimentoId(dto.estabelecimento())
                .build();
    }

    private ProfissionalResponseDto converterResponseUCParaDto(ProfissionalResponseDB responseDB) {
        return ProfissionalResponseDto.builder()
                .profissionalId(responseDB.profissionalId())
                .nome(responseDB.nome())
                .especialidades(responseDB.especialidades())
                .tarifa(responseDB.tarifa())
                .horariosDisponiveis(responseDB.horariosDisponiveis())
                .estabelecimentoId(responseDB.estabelecimentoId())
                .build();
    }
}
