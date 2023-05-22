package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "configuracoes", schema = "public")
@AuditTable(value = "configuracoes_audit", schema = "audit")
public class Configuracao extends AbstractEntity{

    @Getter @Setter
    @NotNull(message = "Campo valorHora não enviado")
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;

    @Getter @Setter
    @NotNull(message = "Campo valorMinutoMulta não enviado")
    @Column(name = "valor_minuto_multa", nullable = false)
    private BigDecimal valorMinutoMulta;

    @Getter @Setter
    @NotNull(message = "Campo inicioExpediente não enviado")
    @Column(name = "inicio_expediente", nullable = false)
    private LocalTime inicioExpediente;

    @Getter @Setter
    @NotNull(message = "Campo fimExpediente não enviado")
    @Column(name = "fim_expediente", nullable = false)
    private LocalTime fimExpediente;

    @Getter @Setter
    @NotNull(message = "Campo tempoParaDesconto não enviado")
    @Column(name = "tempo_para_desconto", nullable = false)
    private Integer tempoParaDesconto;

    @Getter @Setter
    @NotNull(message = "Campo tempoDesconto não enviado")
    @Column(name = "tempo_desconto", nullable = false)
    private BigDecimal tempoDesconto;

    @Getter @Setter
    @NotNull(message = "Campo gerarDesconto não enviado")
    @Column(name = "gerar_desconto", nullable = false)
    private Boolean gerarDesconto;

    @Getter @Setter
    @Column(name = "vagas_moto", nullable = true)
    private Integer vagasMoto;

    @Getter @Setter
    @NotNull
    @Column(name = "vagas_carro", nullable = true)
    private Integer vagasCarro;

    @Getter @Setter
    @NotNull
    @Column(name = "vagas_van", nullable = true)
    private Integer vagasVan;


    @PrePersist
    private void prePersist(){
        this.vagasCarro = 0;
        this.vagasVan = 0;
        this.vagasMoto = 0;
    }

}
