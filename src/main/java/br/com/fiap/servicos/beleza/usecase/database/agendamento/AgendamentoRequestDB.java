package br.com.fiap.servicos.beleza.usecase.database.agendamento;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AgendamentoRequestDB(
        LocalDateTime dataHora,
        Long clienteId,
        Long profissionalId,
        Long servicoId) {
}
