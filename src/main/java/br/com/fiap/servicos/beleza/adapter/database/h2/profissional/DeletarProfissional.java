package br.com.fiap.servicos.beleza.adapter.database.h2.profissional;

import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.profissional.IDeletarProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarProfissional implements IDeletarProfissional {

    private final ProfissionalRepository profissionalRepository;

    @Override
    @Transactional
    public void deletarProfissional(long profissionalId) {
        profissionalRepository.deleteById(profissionalId);
    }
}
