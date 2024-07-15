package br.com.fiap.servicos.beleza.adapter.database.h2.cliente;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ClienteResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.cliente.ICadastrarCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarCliente implements ICadastrarCliente {

    private final ClienteRepository clienteRepository;

    @Transactional
    @Override
    public ClienteResponseDB execute(ClienteRequestDB request) {
        var cliente = montarClienteRequest(request);
        clienteRepository.save(cliente);
        return converterResponseDatabaseParaUC(cliente);
    }

    private TabelaCliente montarClienteRequest(ClienteRequestDB requestDB) {
        return TabelaCliente.builder()
                .nome(requestDB.nome())
                .email(requestDB.email())
                .build();
    }

    private ClienteResponseDB converterResponseDatabaseParaUC(TabelaCliente tabela) {
        return ClienteResponseDB.builder()
                .clienteId(tabela.getId())
                .nome(tabela.getNome())
                .email(tabela.getEmail())
                .build();
    }
}
