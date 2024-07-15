package br.com.fiap.servicos.beleza.usecase.database.estabelecimento;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EstabelecimentoServicoResponseDB (
        long servicoId,
        String nomeServico,
        String descricaoServico,
        BigDecimal valor) {
}
