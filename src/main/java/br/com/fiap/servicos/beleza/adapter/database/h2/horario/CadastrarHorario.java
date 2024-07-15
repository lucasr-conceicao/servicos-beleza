package br.com.fiap.servicos.beleza.adapter.database.h2.horario;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaCliente;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaHorario;
import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.repository.ClienteRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.HorarioRepository;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.horario.HorarioRequestDB;
import br.com.fiap.servicos.beleza.usecase.database.horario.HorarioResponseDB;
import br.com.fiap.servicos.beleza.usecase.database.horario.ICadastrarHorario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarHorario implements ICadastrarHorario {

    private final HorarioRepository horarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;


    @Transactional
    @Override
    public HorarioResponseDB execute(HorarioRequestDB request) {
        var cliente = buscarCliente(request.clienteId());
        var profissional = buscarProfissional(request.profissionalId());
        var horario = montarHorarioRequest(request, profissional, cliente);
        horarioRepository.save(horario);
        return converterResponseDatabaseParaUC(horario);
    }

    private TabelaHorario montarHorarioRequest(HorarioRequestDB requestDB, TabelaProfissional profissional, TabelaCliente cliente) {
        return TabelaHorario.builder()
                .data(requestDB.data())
                .horaInicio(requestDB.horaInicio())
                .horaFim(requestDB.horaFim())
                .profissional(profissional)
                .cliente(cliente)
                .build();
    }

    private HorarioResponseDB converterResponseDatabaseParaUC(TabelaHorario tabela) {
        return HorarioResponseDB.builder()
                .horarioId(tabela.getId())
                .data(tabela.getData())
                .horaInicio(tabela.getHoraInicio())
                .horaFim(tabela.getHoraFim())
                .profissionalId(tabela.getProfissional().getId())
                .clienteId(tabela.getCliente().getId())
                .build();
    }

    @Transactional(readOnly = true)
    private TabelaCliente buscarCliente(long clientId) {
        return  clienteRepository.findById(clientId).orElseThrow(
                () -> new RuntimeException(""));
    }

    @Transactional(readOnly = true)
    private TabelaProfissional buscarProfissional(long profissionalId) {
        return profissionalRepository.findById(profissionalId).orElseThrow(
                () -> new RuntimeException(""));
    }
}
