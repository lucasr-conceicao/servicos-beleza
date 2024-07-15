package br.com.fiap.servicos.beleza.adapter.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_servico")
public class TabelaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private TabelaEstabelecimento estabelecimento;

    @OneToMany(mappedBy = "servico")
    private List<TabelaAgendamento> agendamentos;
}
