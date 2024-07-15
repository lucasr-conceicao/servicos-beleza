package br.com.fiap.servicos.beleza.adapter.database.h2.profissional;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ProfissionalResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.profissional.IBuscarProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarProfissional implements IBuscarProfissional {

    private final ProfissionalRepository profissionalRepository;

    @Override
    @Transactional
    public ProfissionalResponseDB buscarProfissional(long profissionalId) {
        var profissional = profissionalRepository.findById(profissionalId);
        var profissionalValidado = validarProfissional(profissional);
        return converterResponseDatabaseParaUC(profissionalValidado);
    }

    private Optional<TabelaProfissional> validarProfissional(Optional<TabelaProfissional> response) {
        if (!response.isPresent())
            throw new RuntimeException("Error");
        return response;
    }

    private ProfissionalResponseDB converterResponseDatabaseParaUC(Optional<TabelaProfissional> response) {
        return ProfissionalResponseDB.builder()
                .profissionalId(response.get().getId())
                .nome(response.get().getNome())
                .especialidades(response.get().getEspecialidades())
                .tarifa(response.get().getTarifa())
                .horariosDisponiveis(response.get().getHorariosDisponiveis())
                .estabelecimentoId(response.get().getEstabelecimento().getId())
                .build();
    }
}
