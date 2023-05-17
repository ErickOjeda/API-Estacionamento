package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao mov){

        Assert.isTrue(mov.getVeiculo() != null, "Veiculo não informado");
        Assert.isTrue(mov.getCondutor() != null, "Condutor não informada");
        Assert.isTrue(mov.getEntrada() != null, "Entrada não informada");

        final Long id_veiculo = mov.getVeiculo().getId();
        final Veiculo veiculo = this.veiculoRepository.findById(id_veiculo).orElse(null);
        Assert.isTrue(veiculo != null, "Veiculo inválido");

        final Long id_condutor = mov.getVeiculo().getId();
        final Condutor condutor = this.condutorRepository.findById(id_condutor).orElse(null);
        Assert.isTrue(condutor != null, "Condutor inválido");

        this.movimentacaoRepository.save(mov);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Movimentacao mov){

        final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);

        Assert.isTrue((movBanco != null), "Não foi possivel identificar o registro informado");
        Assert.isTrue(movBanco.getId().equals(id), "Não foi possivel identificar o registro informado");

        Assert.isTrue(mov.getVeiculo() != null, "Veiculo não informado");
        Assert.isTrue(mov.getCondutor() != null, "Condutor não informada");
        Assert.isTrue(mov.getEntrada() != null, "Entrada não informada");

        final Long id_veiculo = mov.getVeiculo().getId();
        final Veiculo veiculo = this.veiculoRepository.findById(id_veiculo).orElse(null);
        Assert.isTrue(veiculo != null, "Veiculo inválido");

        final Long id_condutor = mov.getVeiculo().getId();
        final Condutor condutor = this.condutorRepository.findById(id_condutor).orElse(null);
        Assert.isTrue(condutor != null, "Condutor inválido");

        this.movimentacaoRepository.save(mov);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sair(final Long id){

        // Verifica se a movimentação existe
        final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.isTrue(movBanco != null, "Não foi possivel identificar o registro informado");

        // Identifica o horário da saida e calcula o tempo entre os dois horários
        final LocalDateTime saida = LocalDateTime.now();
        Duration duracao = Duration.between(movBanco.getEntrada(), saida);

        // Pega os valores de configuração
        final Configuracao config = this.configuracaoRepository.findById(1L).orElse(null);

        // Pega o desconto do cliente
        final Condutor condutor = this.condutorRepository.findById(movBanco.getCondutor().getId()).orElse(null);

//      final BigDecmial preco = BigDecimal.valueOf();

        movBanco.setSaida(saida);
        movBanco.setTempoHoras(duracao.toHoursPart());
        movBanco.setTempoMinutos(duracao.toMinutesPart());

        Assert.isTrue(config.getValorHora() != null, "Valor da hora não definido em configurações");

//        if(condutor.getTempoDesconto() && condutor.getTempoDesconto() >= 50) {
//            movBanco.setValorDesconto(config.getValorHora().multiply(BigDecimal.valueOf(condutor.getTempoDesconto())));
//        }

        this.movimentacaoRepository.save(movBanco);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id){

        final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.isTrue(movBanco != null, "Registro não encontrado");

        movBanco.setAtivo(false);
        this.movimentacaoRepository.save(movBanco);

    }

}
