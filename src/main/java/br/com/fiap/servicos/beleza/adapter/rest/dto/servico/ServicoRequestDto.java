package br.com.fiap.servicos.beleza.adapter.rest.dto.servico;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ServicoRequestDto (
        String nomeServico,
        String descricaoServico,
        double valor,
        long estabelecimentoId) {
}
