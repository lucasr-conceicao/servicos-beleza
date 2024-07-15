package br.com.fiap.servicos.beleza.usecase.database.avaliacao;

import lombok.Builder;

@Builder
public record AvaliacaoProfissionalRequest (
        long id,
        long profissionalId,
        long clienteId,
        int pontuacao,
        String comentario) {
}
