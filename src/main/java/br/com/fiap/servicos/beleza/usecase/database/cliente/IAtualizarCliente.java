package br.com.fiap.servicos.beleza.usecase.database.cliente;

public interface IAtualizarCliente {

    ClienteResponseDB atualizarCliente(long clienteId, ClienteRequestDB requestDB);
}
