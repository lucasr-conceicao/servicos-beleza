package br.com.fiap.servicos.beleza.adapter.rest.dto.cliente;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ClienteResponseDto(
        Long clienteId,
        String nome,
        String email) {
}
