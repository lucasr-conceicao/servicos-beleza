package br.com.fiap.servicos.beleza.usecase.database.servico;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ServicoRequestDB (
        String nomeServico,
        String descricaoServico,
        BigDecimal valor,
        long estabelecimentoId) {
}
