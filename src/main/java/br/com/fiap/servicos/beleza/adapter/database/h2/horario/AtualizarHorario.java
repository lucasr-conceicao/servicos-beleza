package br.com.fiap.servicos.beleza.adapter.database.h2.horario;

import br.com.fiap.servicos.beleza.adapter.database.repository.HorarioRepository;
import br.com.fiap.servicos.beleza.usecase.database.horario.HorarioRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.horario.HorarioResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.horario.IAtualizarHorario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarHorario implements IAtualizarHorario {

    private final HorarioRepository horarioRepository;

    @Override
    @Transactional
    public HorarioResponseDB atualizarHorario(long horarioId, HorarioRequestDB requestDB) {
        var horario = horarioRepository.findById(horarioId).orElseThrow(() -> new RuntimeException("Horário não encontrado"));
        horario.setData(requestDB.data());
        horario.setHoraInicio(requestDB.horaInicio());
        horario.setHoraFim(requestDB.horaFim());
        horarioRepository.save(horario);
        return HorarioResponseDB.builder()
                .horarioId(horario.getId())
                .data(horario.getData())
                .horaInicio(horario.getHoraInicio())
                .horaFim(horario.getHoraFim())
                .profissionalId(horario.getProfissional().getId())
                .clienteId(horario.getCliente().getId())
                .build();
    }
}