package br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EstabelecimentoResponseProfissionalDto(
        Long profissionalId,
        String nome,
        String especialidades,
        Double tarifa) {
}
