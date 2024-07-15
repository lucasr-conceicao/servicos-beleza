package br.com.fiap.servicos.beleza.adapter.rest.dto.horario;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HorarioResponseDto(
        Long horarioId,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        Long profissionalId,
        Long clienteId) {
}
