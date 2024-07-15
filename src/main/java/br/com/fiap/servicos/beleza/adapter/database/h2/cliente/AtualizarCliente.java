package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.cliente.IAtualizarCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarCliente implements IAtualizarCliente {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDB atualizarCliente(long clienteId, ClienteRequestDB requestDB) {
        var cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setNome(requestDB.nome());
        cliente.setEmail(requestDB.email());
        clienteRepository.save(cliente);
        return ClienteResponseDB.builder()
                .clienteId(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .build();
    }
}
