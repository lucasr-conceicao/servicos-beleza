package br.com.fiap.servicos.beleza.adapter.rest.dto.profissional;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfissionalRequestDto(
        String nome,
        String especialidade,
        Double tarifa,
        String horariosDisponiveis,
        long estabelecimento) {
}
