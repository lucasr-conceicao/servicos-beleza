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
@Table(name = "tb_avaliacao_profissional")
public class TabelaAvaliacaoProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long profissionalId;
    private Long clienteId;
    private int pontuacao;
    private String comentario;
}
