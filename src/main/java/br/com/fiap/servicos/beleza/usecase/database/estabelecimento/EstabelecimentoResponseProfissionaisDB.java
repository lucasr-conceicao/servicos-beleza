package br.com.fiap.servicos.beleza.usecase.database.estabelecimento;

import lombok.Builder;

@Builder
public record EstabelecimentoResponseProfissionaisDB(
        Long profissionalId,
        String nome,
        String especialidades,
        Double tarifa) {
}
