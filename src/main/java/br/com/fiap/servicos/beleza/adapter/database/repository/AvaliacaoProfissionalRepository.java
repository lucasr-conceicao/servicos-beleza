package br.com.fiap.servicos.beleza.adapter.database.repository;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAvaliacaoProfissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoProfissionalRepository extends JpaRepository<TabelaAvaliacaoProfissional, Long> {

}