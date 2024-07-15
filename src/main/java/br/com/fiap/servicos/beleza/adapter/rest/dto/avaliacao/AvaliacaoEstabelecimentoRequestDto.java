package br.com.fiap.servicos.beleza.adapter.rest.dto.avaliacao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AvaliacaoEstabelecimentoRequestDto (
        long id,
        long estabelecimentoId,
        long clienteId,
        int pontuacao,
        String comentario) {
}
