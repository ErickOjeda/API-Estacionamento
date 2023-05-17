package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;


@Entity
@Audited
@Table(name = "veiculos", schema = "public")
@AuditTable(value = "veiculos_audit", schema = "audit")
public class Veiculo extends AbstractEntity{

    @Getter @Setter
    @Column(name = "placa", length = 20, nullable = false)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", length = 10, nullable = false)
    private Tipo tipo;

    @Getter @Setter
    @Column(name = "ano", length = 5, nullable = false)
    private Integer ano;

}

