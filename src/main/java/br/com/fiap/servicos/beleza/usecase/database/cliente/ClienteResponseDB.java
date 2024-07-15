package br.com.fiap.servicos.beleza.usecase.database.cliente;

import lombok.Builder;

@Builder
public record ClienteResponseDB(
        long clienteId,
        String nome,
        String email) {
}
