package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoResponseDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.servico.ServicoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.servico.ServicoResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.EstabelecimentoResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.servico.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class ServicoController {

    private final ICadastrarServico cadastrarServico;
    private final IAtualizarServico atualizarServico;
    private final IDeletarServico deletarServico;
//    private final IBuscarServico buscarServico;

    @PostMapping("/servico")
    public ResponseEntity<ServicoResponseDto> cadastrarServico(@RequestBody ServicoRequestDto requestDto) {
        var response = cadastrarServico.cadastrarServico(montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(converterResponseUCParaDto(response));
    }

    @PutMapping("/servico/{servicoId}")
    public ResponseEntity<ServicoResponseDto> atualizarServico(@PathVariable long servicoId, @RequestBody ServicoRequestDto requestDto) {
        var response = atualizarServico.atualizarServico(servicoId, montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @DeleteMapping("/servico/{servicoId}")
    public ResponseEntity<Void> deletarServico(@PathVariable long servicoId) {
        deletarServico.deletarServico(servicoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ServicoRequestDB montarRequest(ServicoRequestDto dto) {
        return ServicoRequestDB.builder()
                .nomeServico(dto.nomeServico())
                .descricaoServico(dto.descricaoServico())
                .valor(BigDecimal.valueOf(dto.valor()))
                .estabelecimentoId(dto.estabelecimentoId())
                .build();
    }

    private ServicoResponseDto converterResponseUCParaDto(ServicoResponseDB responseDB) {
        return ServicoResponseDto.builder()
                .servicoId(responseDB.servicoId())
                .nomeServico(responseDB.nomeServico())
                .descricaoServico(responseDB.descricaoServico())
                .valor(responseDB.valor())
                .estabelecimentoId(responseDB.estabelecimentoId())
                .build();
    }
}
