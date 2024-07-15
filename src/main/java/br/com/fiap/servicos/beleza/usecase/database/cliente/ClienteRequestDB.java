package br.com.fiap.servicos.beleza.usecase.database.cliente;

import lombok.Builder;

@Builder
public record ClienteRequestDB(
        String nome,
        String email) {
}
