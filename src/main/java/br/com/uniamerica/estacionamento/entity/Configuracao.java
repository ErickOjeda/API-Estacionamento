package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "configuracoes", schema = "public")
@AuditTable(value = "configuracoes_audit", schema = "audit")
public class Configuracao extends AbstractEntity{

    @Getter @Setter
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_minuto_multa", nullable = false)
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicio_expediente", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fim_expediente", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempo_para_desconto", nullable = false)
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempo_desconto", nullable = false)
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerar_desconto", nullable = false)
    private Boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagas_moto", nullable = true)
    private Integer vagasMoto;
    @Getter @Setter
    @Column(name = "vagas_carro", nullable = true)
    private Integer vagasCarro;
    @Getter @Setter
    @Column(name = "vagas_van", nullable = true)
    private Integer vagasVan;


}
