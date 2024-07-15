package br.com.fiap.servicos.beleza.adapter.database.h2.horario;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaHorario;
import br.com.fiap.servicos.beleza.adapter.database.repository.HorarioRepository;
import br.com.fiap.servicos.beleza.usecase.database.horario.HorarioResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.horario.IBuscarHorario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarHorario implements IBuscarHorario {

    private final HorarioRepository horarioRepository;

    @Override
    @Transactional
    public HorarioResponseDB buscarHorario(long horarioId) {
        var horario = horarioRepository.findById(horarioId);
        var horarioValidado = validarHorario(horario);
        return converterResponseDatabaseParaUC(horarioValidado);
    }

    private Optional<TabelaHorario> validarHorario(Optional<TabelaHorario> response) {
        if (!response.isPresent())
            throw new RuntimeException("Error");
        return response;
    }

    private HorarioResponseDB converterResponseDatabaseParaUC(Optional<TabelaHorario> response) {
        return HorarioResponseDB.builder()
                .horarioId(response.get().getId())
                .data(response.get().getData())
                .horaInicio(response.get().getHoraInicio())
                .horaFim(response.get().getHoraFim())
                .profissionalId(response.get().getProfissional().getId())
                .clienteId(response.get().getCliente().getId())
                .build();
    }
}
