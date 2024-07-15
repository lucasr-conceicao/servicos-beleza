package br.com.fiap.servicos.beleza.adapter.rest.controller;

import br.com.fiap.servicos.beleza.adapter.rest.dto.cliente.ClienteRequestDto;
import br.com.fiap.servicos.beleza.adapter.rest.dto.cliente.ClienteResponseDto;
import br.com.fiap.servicos.beleza.usecase.database.cliente.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos-beleza/v1")
@RequiredArgsConstructor
public class ClienteController {

    private final ICadastrarCliente cadastrarCliente;
    private final IBuscarCliente buscarCliente;
    private final IAtualizarCliente atualizarCliente;
    private final IDeletarCliente deletarCliente;

    @PostMapping("/cliente")
    public ResponseEntity<ClienteResponseDto> cadastrarCliente(@RequestBody ClienteRequestDto requestDto) {
        var response = cadastrarCliente.execute(montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(converterResponseUCParaDto(response));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ClienteResponseDto> buscarCliente(@PathVariable(value = "clienteId") long clienteId) {
        var response = buscarCliente.buscarCliente(clienteId);
        return response != null ? ResponseEntity.ok(converterResponseUCParaDto(response)) : ResponseEntity.notFound().build();
    }

    @PutMapping("/cliente/{clienteId}")
    public ResponseEntity<ClienteResponseDto> atualizarCliente(@PathVariable long clienteId, @RequestBody ClienteRequestDto requestDto) {
        var response = atualizarCliente.atualizarCliente(clienteId, montarRequest(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(converterResponseUCParaDto(response));
    }

    @DeleteMapping("/cliente/{clienteId}")
    public ResponseEntity<Void> deletarCliente(@PathVariable long clienteId) {
        deletarCliente.deletarCliente(clienteId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ClienteRequestDB montarRequest(ClienteRequestDto dto) {
        return ClienteRequestDB.builder()
                .nome(dto.nome())
                .email(dto.email())
                .build();
    }

    private ClienteResponseDto converterResponseUCParaDto(ClienteResponseDB responseDB) {
        return ClienteResponseDto.builder()
                .clienteId(responseDB.clienteId())
                .nome(responseDB.nome())
                .email(responseDB.email())
                .build();
    }
}
