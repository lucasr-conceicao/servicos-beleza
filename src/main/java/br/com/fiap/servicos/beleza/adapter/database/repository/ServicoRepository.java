package br.com.fiap.servicos.beleza.adapter.database.repository;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaServico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<TabelaServico, Long> {
}
