package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoResponseDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoResponseProfissionalDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento.EstabelecimentoResponseServicoDto;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class EstabelecimentoController {

    private final ICadastrarEstabelecimento cadastrarEstabelecimento;
    private final IBuscarEstabelecimento buscarEstabelecimento;
    private final IAtualizarEstabelecimento atualizarEstabelecimento;
    private final IDeletarEstabelecimento deletarEstabelecimento;


    @PostMapping("/estabelecimento")
    public ResponseEntity<EstabelecimentoResponseDto> cadastrarEstabelecimento(@RequestBody EstabelecimentoRequestDto requestDto) {
        var response = cadastrarEstabelecimento.execute(montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(converterResponseUCParaDtoCreated(response));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<EstabelecimentoResponseDto> buscarEstabelecimento(@PathVariable(value = "estabelecimentoId") long estabelecimentoId) {
        var response = buscarEstabelecimento.buscarEstabelecimento(estabelecimentoId);
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @PutMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<EstabelecimentoResponseDto> atualizarEstabelecimento(@PathVariable long estabelecimentoId, @RequestBody EstabelecimentoRequestDto requestDto) {
        var response = atualizarEstabelecimento.atualizarEstabelecimento(estabelecimentoId, montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @DeleteMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<Void> deletarEstabelecimento(@PathVariable long estabelecimentoId) {
        deletarEstabelecimento.deletarEstabelecimento(estabelecimentoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private EstabelecimentoRequestDB montarRequest(EstabelecimentoRequestDto dto) {
        return EstabelecimentoRequestDB.builder()
                .nomeEstabelecimento(dto.nomeEstabelecimento())
                .endereco(dto.endereco())
                .horarioFuncionamento(dto.horarioFuncionamento())
                .build();
    }

    private EstabelecimentoResponseDto converterResponseUCParaDtoCreated(EstabelecimentoResponseDB responseDB) {
        return EstabelecimentoResponseDto.builder()
                .estabelecimentoId(responseDB.estabelecimentoId())
                .nomeEstabelecimento(responseDB.nomeEstabelecimento())
                .endereco(responseDB.endereco())
                .horarioFuncionamento(responseDB.horarioFuncionamento())
                .build();
    }

    private EstabelecimentoResponseDto converterResponseUCParaDto(EstabelecimentoResponseDB responseDB) {
        return EstabelecimentoResponseDto.builder()
                .estabelecimentoId(responseDB.estabelecimentoId())
                .nomeEstabelecimento(responseDB.nomeEstabelecimento())
                .endereco(responseDB.endereco())
                .horarioFuncionamento(responseDB.horarioFuncionamento())
                .servicoDto(responseDB.servicos().stream().map(this::converterResponseServicoUCParaResponseDto)
                        .collect(Collectors.toList()))
                .profissionalDto(responseDB.profissionais().stream().map(this::converterResponseProfissionalUCParaResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private EstabelecimentoResponseServicoDto converterResponseServicoUCParaResponseDto(EstabelecimentoServicoResponseDB servico) {
        return EstabelecimentoResponseServicoDto.builder()
                .servicoId(servico.servicoId())
                .nomeServico(servico.nomeServico())
                .descricaoServico(servico.descricaoServico())
                .valor(servico.valor())
                .build();
    }

    private EstabelecimentoResponseProfissionalDto converterResponseProfissionalUCParaResponseDto(EstabelecimentoResponseProfissionaisDB profissional) {
        return EstabelecimentoResponseProfissionalDto.builder()
                .profissionalId(profissional.profissionalId())
                .nome(profissional.nome())
                .especialidades(profissional.especialidades())
                .tarifa(profissional.tarifa())
                .build();
    }
}
