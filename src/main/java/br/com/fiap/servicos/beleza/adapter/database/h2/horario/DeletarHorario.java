package br.com.fiap.servicos.beleza.adapter.database.h2.horario;

import br.com.fiap.servicos.beleza.adapter.database.repository.HorarioRepository;
import br.com.fiap.servicos.beleza.usecase.database.horario.IDeletarHorario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarHorario implements IDeletarHorario {

    private final HorarioRepository horarioRepository;

    @Override
    @Transactional
    public void deletarHorario(long horarioId) {
        horarioRepository.deleteById(horarioId);
    }
}
