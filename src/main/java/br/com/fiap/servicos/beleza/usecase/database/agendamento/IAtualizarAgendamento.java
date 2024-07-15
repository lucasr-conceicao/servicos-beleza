package br.com.fiap.servicos.beleza.usecase.database.agendamento;

public interface IAtualizarAgendamento {

    AgendamentoResponseDB atualizarAgendamento(long agendamentoId, AgendamentoRequestDB requestDB);
}
