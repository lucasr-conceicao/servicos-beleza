package br.com.fiap.servicos.beleza.usecase.database.horario;

public interface IAtualizarHorario {

    HorarioResponseDB atualizarHorario(long horarioId, HorarioRequestDB requestDB);
}
