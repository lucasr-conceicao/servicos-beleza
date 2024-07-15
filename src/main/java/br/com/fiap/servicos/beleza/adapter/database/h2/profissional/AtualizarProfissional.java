package br.com.fiap.servicos.beleza.adapter.database.h2.profissional;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ProfissionalRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ProfissionalResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.profissional.IAtualizarProfissional;
import br.com.fiap.servicos.beleza.usecase.database.profissional.IDeletarProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarProfissional implements IAtualizarProfissional {

    private final ProfissionalRepository profissionalRepository;

    @Override
    @Transactional
    public ProfissionalResponseDB atualizarProfissional(long profissionalId, ProfissionalRequestDB requestDB) {
        var profissional = profissionalRepository.findById(profissionalId).orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        profissional.setNome(requestDB.nome());
        profissional.setEspecialidades(requestDB.especialidades());
        profissional.setTarifa(requestDB.tarifa());
        profissional.setHorariosDisponiveis(requestDB.horariosDisponiveis());
        profissionalRepository.save(profissional);
        return ProfissionalResponseDB.builder()
                .profissionalId(profissional.getId())
                .nome(profissional.getNome())
                .especialidades(profissional.getEspecialidades())
                .tarifa(profissional.getTarifa())
                .horariosDisponiveis(profissional.getHorariosDisponiveis())
                .estabelecimentoId(profissional.getEstabelecimento().getId())
                .build();
    }
}
