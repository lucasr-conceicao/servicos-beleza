package br.com.fiap.servicos.beleza.usecase.database.profissional;

public interface IAtualizarProfissional {

    ProfissionalResponseDB atualizarProfissional(long profissionalId, ProfissionalRequestDB requestDB);
}
