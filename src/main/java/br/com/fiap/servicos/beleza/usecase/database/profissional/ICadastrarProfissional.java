package br.com.fiap.servicos.beleza.usecase.database.profissional;

public interface ICadastrarProfissional {

    ProfissionalResponseDB execute(ProfissionalRequestDB requestDB);
}
