package br.com.fiap.servicos.beleza.usecase.database.agendamento;

public interface ICadastrarAgendamento {

    AgendamentoResponseDB execute(AgendamentoRequestDB requestDB);
}
