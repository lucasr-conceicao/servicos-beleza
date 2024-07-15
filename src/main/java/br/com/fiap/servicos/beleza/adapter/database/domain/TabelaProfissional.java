package br.com.fiap.servicos.beleza.adapter.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_profissional")
public class TabelaProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String especialidades;

    private Double tarifa;

    private String horariosDisponiveis;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private TabelaEstabelecimento estabelecimento;

    @OneToMany(mappedBy = "profissional")
    private List<TabelaAgendamento> agendamentos;
}
