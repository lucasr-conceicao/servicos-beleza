package br.com.fiap.servicos.beleza.usecase.database.horario;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record HorarioResponseDB(
        long horarioId,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        Long profissionalId,
        Long clienteId) {
}
