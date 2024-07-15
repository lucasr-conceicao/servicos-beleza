package br.com.fiap.servicos.beleza.adapter.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_agendamento")
public class TabelaAgendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private TabelaCliente cliente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private TabelaProfissional profissional;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private TabelaServico servico;
}
