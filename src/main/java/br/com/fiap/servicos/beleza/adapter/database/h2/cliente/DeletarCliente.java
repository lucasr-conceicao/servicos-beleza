package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.usecase.database.cliente.IDeletarCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarCliente implements IDeletarCliente {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public void deletarCliente(long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}