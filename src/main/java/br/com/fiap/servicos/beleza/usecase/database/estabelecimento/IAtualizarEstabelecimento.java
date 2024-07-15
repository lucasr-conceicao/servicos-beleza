package br.com.fiap.servicos.beleza.usecase.database.estabelecimento;

public interface IAtualizarEstabelecimento {

    EstabelecimentoResponseDB atualizarEstabelecimento(long estabelecimentoId, EstabelecimentoRequestDB requestDB);
}

