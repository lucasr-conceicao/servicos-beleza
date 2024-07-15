package br.com.fiap.servicos.beleza.usecase.database.estabelecimento;

public interface ICadastrarEstabelecimento {

    EstabelecimentoResponseDB execute(EstabelecimentoRequestDB requestDB);
}
