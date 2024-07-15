package br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EstabelecimentoRequestDto(
        String nomeEstabelecimento,
        String endereco,
        String horarioFuncionamento) {
}