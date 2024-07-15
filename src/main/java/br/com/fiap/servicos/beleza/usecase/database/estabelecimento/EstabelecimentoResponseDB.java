package br.com.fiap.servicos.beleza.usecase.database.estabelecimento;

import lombok.Builder;

import java.util.List;

@Builder
public record EstabelecimentoResponseDB(
        long estabelecimentoId,
        String nomeEstabelecimento,
        String endereco,
        String horarioFuncionamento,
        List<EstabelecimentoServicoResponseDB> servicos,
        List<EstabelecimentoResponseProfissionaisDB> profissionais) {
}
