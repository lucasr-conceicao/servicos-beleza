package br.com.fiap.servicos.beleza.adapter.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_avaliacao_estabelecimento")
public class TabelaAvaliacaoEstabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estabelecimentoId;

    private Long clienteId;

    private int pontuacao; // pontuação da avaliação (ex: de 1 a 5 estrelas)

    private String comentario; // comentário opcional do cliente
}
