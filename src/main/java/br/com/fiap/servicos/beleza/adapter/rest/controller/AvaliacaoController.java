package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.avaliacao.AvaliacaoEstabelecimentoRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.avaliacao.AvaliacaoProfissionalRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.cliente.ClienteRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.cliente.ClienteResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.AvaliacaoEstabelecimentoRequest;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.AvaliacaoProfissionalRequest;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.ICadastrarAvaliacaoEstabelecimento;
import br.com.fiap.servicos.beleza.usecase.database.avaliacao.ICadastrarAvaliacaoProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final ICadastrarAvaliacaoEstabelecimento cadastrarAvaliacaoEstabelecimento;
    private final ICadastrarAvaliacaoProfissional cadastrarAvaliacaoProfissional;

    @PostMapping("/avaliacao-estabelecimento")
    public ResponseEntity<Void> avaliarEstabelecimento(@RequestBody AvaliacaoEstabelecimentoRequestDto requestDto) {
        cadastrarAvaliacaoEstabelecimento.cadastrarAvaliacaoEstabelecimento(montarRequestEstabelecimento(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/avaliacao-profissional")
    public ResponseEntity<Void> avaliarProfissional(@RequestBody AvaliacaoProfissionalRequestDto requestDto) {
        cadastrarAvaliacaoProfissional.cadastrarAvaliacaoProfissional(montarRequestProfissional(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private AvaliacaoEstabelecimentoRequest montarRequestEstabelecimento(AvaliacaoEstabelecimentoRequestDto requestDto) {
        return AvaliacaoEstabelecimentoRequest.builder()
                .id(requestDto.id())
                .comentario(requestDto.comentario())
                .pontuacao(requestDto.pontuacao())
                .clienteId(requestDto.clienteId())
                .estabelecimentoId(requestDto.estabelecimentoId())
                .build();
    }

    private AvaliacaoProfissionalRequest montarRequestProfissional(AvaliacaoProfissionalRequestDto requestDto) {
        return AvaliacaoProfissionalRequest.builder()
                .id(requestDto.id())
                .clienteId(requestDto.clienteId())
                .pontuacao(requestDto.pontuacao())
                .comentario(requestDto.comentario())
                .profissionalId(requestDto.profissionalId())
                .build();
    }
}
