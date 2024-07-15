package br.com.fiap.servicos.beleza.adapter.database.repository;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<TabelaAgendamento, Long> {
}
