package br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EstabelecimentoResponseServicoDto (
        long servicoId,
        String nomeServico,
        String descricaoServico,
        BigDecimal valor) {
}
