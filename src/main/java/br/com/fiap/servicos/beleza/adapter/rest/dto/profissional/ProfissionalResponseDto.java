package br.com.fiap.servicos.beleza.adapter.rest.dto.profissional;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfissionalResponseDto(
        Long profissionalId,
        String nome,
        String especialidades,
        Double tarifa,
        String horariosDisponiveis,
        Long estabelecimentoId) {
}
