package br.com.fiap.servicos.beleza.adapter.database.repository;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<TabelaProfissional, Long> {
}
