package br.com.fiap.servicos.beleza.adapter.rest.dto.horario;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HorarioRequestDto(
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        Long profissionalId,
        Long clienteId) {
}
