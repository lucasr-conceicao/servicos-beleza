package br.com.fiap.servicos.beleza.adapter.rest.dto.agendamento;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AgendamentoRequestDto(
        LocalDateTime dataHora,
        Long clienteId,
        Long profissionalId,
        Long servicoId) {
}
