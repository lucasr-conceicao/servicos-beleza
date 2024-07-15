package br.com.fiap.servicos.beleza.usecase.database.servico;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ServicoResponseDB (
        long servicoId,
        String nomeServico,
        String descricaoServico,
        BigDecimal valor,
        long estabelecimentoId) {
}
