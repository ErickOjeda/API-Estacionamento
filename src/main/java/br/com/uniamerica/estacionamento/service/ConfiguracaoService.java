package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalTime;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Configuracao config){

        Assert.isTrue(config.getValorHora() != null, "Valor hora não informado");
        Assert.isTrue(config.getValorMinutoMulta() != null, "Valor minuto multa não informado");
        Assert.isTrue(config.getInicioExpediente() != null, "Inicio expediente não informado");
        Assert.isTrue(config.getFimExpediente() != null, "Fim expediente não informado");
        Assert.isTrue(config.getTempoParaDesconto() != null, "Tempo desconto não informado");
        Assert.isTrue(config.getGerarDesconto() != null, "Gerar desconto não informado");

        this.configuracaoRepository.save(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Configuracao config){

        final Configuracao configBanco = this.configuracaoRepository.findById(id).orElse(null);

        Assert.isTrue(configBanco != null, "Não foi possivel identificar o registro informado 1");
        Assert.isTrue(config.getId().equals(id), "Não foi possivel identificar o registro informado 2");

        Assert.isTrue(config.getValorHora() != null, "Valor hora não informado");
        Assert.isTrue(config.getValorMinutoMulta() != null, "Valor minuto multa não informado");
        Assert.isTrue(config.getInicioExpediente() != null, "Inicio expediente não informado");
        Assert.isTrue(config.getFimExpediente() != null, "Fim expediente não informado");
        Assert.isTrue(config.getTempoParaDesconto() != null, "Tempo desconto não informado");
        Assert.isTrue(config.getGerarDesconto() != null, "Gerar desconto não informado");
        Assert.isTrue(config.getVagasCarro()  != null, "Vagas de carro não informado");
        Assert.isTrue(config.getVagasVan() != null, "Vagas de van não informado");
        Assert.isTrue(config.getVagasMoto() != null, "Vagas de moto não informado");

        this.configuracaoRepository.save(config);
    }
}
