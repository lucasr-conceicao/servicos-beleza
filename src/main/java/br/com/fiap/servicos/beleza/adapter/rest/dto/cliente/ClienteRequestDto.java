package br.com.fiap.servicos.beleza.adapter.rest.dto.cliente;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ClienteRequestDto(
        String nome,
        String email) {
}
