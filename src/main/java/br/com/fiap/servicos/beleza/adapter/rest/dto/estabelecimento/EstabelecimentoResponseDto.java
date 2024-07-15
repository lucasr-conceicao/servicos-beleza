package br.com.fiap.servicos.beleza.adapter.rest.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EstabelecimentoResponseDto(
        long estabelecimentoId,
        String nomeEstabelecimento,
        String endereco,
        String horarioFuncionamento,
        List<EstabelecimentoResponseServicoDto> servicoDto,
        List<EstabelecimentoResponseProfissionalDto> profissionalDto) {
}
