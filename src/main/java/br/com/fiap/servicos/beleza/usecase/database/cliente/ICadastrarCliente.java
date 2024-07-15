package br.com.fiap.servicos.beleza.usecase.database.cliente;

public interface ICadastrarCliente {

    ClienteResponseDB execute(ClienteRequestDB requestDB);
}
