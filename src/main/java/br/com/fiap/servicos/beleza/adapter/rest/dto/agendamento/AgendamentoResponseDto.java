package br.com.fiap.servicos.beleza.adapter.rest.dto.agendamento;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AgendamentoResponseDto(
        Long agendamentoId,
        LocalDateTime dataHora,
        Long clienteId,
        Long profissionalId,
        Long servicoId) {
}
