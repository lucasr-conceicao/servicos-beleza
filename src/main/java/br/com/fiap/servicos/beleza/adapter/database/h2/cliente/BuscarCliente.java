package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.cliente.IBuscarCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarCliente implements IBuscarCliente {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDB buscarCliente(long clienteId) {
        var cliente = clienteRepository.findById(clienteId);
        var clienteValidado = validarCliente(cliente);
        return converterResponseDatabaseParaUC(clienteValidado);
    }

    private Optional<TabelaCliente> validarCliente(Optional<TabelaCliente> response) {
        if (!response.isPresent())
            throw new NotFoundException("Cliente não encontrado.");
        return response;
    }

    private ClienteResponseDB converterResponseDatabaseParaUC(Optional<TabelaCliente> response) {
        return ClienteResponseDB.builder()
                .clienteId(response.get().getId())
                .nome(response.get().getNome())
                .email(response.get().getEmail())
                .build();
    }
}
