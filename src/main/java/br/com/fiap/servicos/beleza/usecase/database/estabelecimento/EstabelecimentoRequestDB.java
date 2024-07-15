package br.com.fiap.servicos.beleza.usecase.database.estabelecimento;

import lombok.Builder;

@Builder
public record EstabelecimentoRequestDB(
        String nomeEstabelecimento,
        String endereco,
        String horarioFuncionamento) {
}
