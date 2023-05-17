package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Audited
@Table(name = "movimentacoes", schema = "public")
@AuditTable(value = "movimentacoes_audit", schema = "audit")
public class Movimentacao extends AbstractEntity {

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_condutor", nullable = false)
    private Condutor condutor;

    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Getter @Setter
    @Column(name = "saida", nullable = true)
    private LocalDateTime saida;

    @Getter @Setter
    @Column(name = "tempo_horas", nullable = true)
    private Integer tempoHoras;

    @Getter @Setter
    @Column(name = "tempo_minutos", nullable = true)
    private Integer tempoMinutos;

    @Getter @Setter
    @Column(name = "tempo_desconto", nullable = true)
    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column(name = "tempo_multa", nullable = true)
    private LocalTime tempoMulta;

    @Getter @Setter
    @Column(name = "valor_total", nullable = true)
    private BigDecimal valorTotal;

    @Getter @Setter
    @Column(name = "valor_desconto", nullable = true)
    private BigDecimal valorDesconto;

    @Getter @Setter
    @Column(name = "valor_multa", nullable = true)
    private BigDecimal valorMulta;

    @Getter @Setter
    @Column(name = "valor_hora", nullable = true)
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor_minuto_multa", nullable = true)
    private BigDecimal valorMinutoMulta;

}
