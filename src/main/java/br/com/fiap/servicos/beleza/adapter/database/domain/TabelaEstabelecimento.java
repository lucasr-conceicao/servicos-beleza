package br.com.fiap.servicos.beleza.adapter.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_estabelecimento")
public class TabelaEstabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String endereco;

    private String horariosFuncionamento;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL)
    private List<TabelaServico> servicos;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL)
    private List<TabelaProfissional> profissionais;

}
