package br.com.fiap.servicos.beleza.adapter.database.repository;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaAvaliacaoEstabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoEstabelecimentoRepository extends JpaRepository<TabelaAvaliacaoEstabelecimento, Long> {
}