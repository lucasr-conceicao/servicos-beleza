package br.com.fiap.servicos.beleza.adapter.rest.dto.avaliacao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AvaliacaoProfissionalRequestDto (
        long id,
        long profissionalId,
        long clienteId,
        int pontuacao,
        String comentario) {
}
