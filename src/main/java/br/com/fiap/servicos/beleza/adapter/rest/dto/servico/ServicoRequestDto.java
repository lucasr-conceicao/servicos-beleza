package br.com.fiap.servicos.beleza.adapter.rest.dto.servico;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ServicoRequestDto (
        String nomeServico,
        String descricaoServico,
        BigDecimal valor,
        long estabelecimentoId) {
}
