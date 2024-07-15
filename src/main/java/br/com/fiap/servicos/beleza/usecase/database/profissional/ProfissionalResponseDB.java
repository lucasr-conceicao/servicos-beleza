package br.com.fiap.servicos.beleza.usecase.database.profissional;

import lombok.Builder;

@Builder
public record ProfissionalResponseDB(
        long profissionalId,
        String nome,
        String especialidades,
        Double tarifa,
        String horariosDisponiveis,
        Long estabelecimentoId) {
}
