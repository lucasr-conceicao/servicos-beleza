package br.com.fiap.servicos.beleza.usecase.database.avaliacao;

import lombok.Builder;

@Builder
public record AvaliacaoEstabelecimentoRequest (
        long id,
        long estabelecimentoId,
        long clienteId,
        int pontuacao,
        String comentario) {
}
